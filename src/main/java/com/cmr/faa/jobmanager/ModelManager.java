package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.DAO.Models.ModelDao;
import com.cmr.faa.DAO.Models.ModelsDataLoader;
import com.cmr.faa.pojo.Model;
import com.cmr.faa.repositories.ModelRepository;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ModelManager extends Manager {
  final static Logger log = LoggerFactory.getLogger(ModelManager.class);

  @Autowired
  ModelRepository modelRepository;

  @Autowired
  private ModelsDataLoader modelsDataLoader;
  private List<Model> modelList;

  public void loadModelsFromSpreadsheet(String modelSpreadsheetFileName) {
    modelList = modelsDataLoader.load(modelSpreadsheetFileName);
    log.debug(modelList.size() + " Read from Models Spreadsheet");
    save();
  }


  public void loadModelsFromAccess(String accessDatabaseFileName) {
    Database db;
    if (checkExistence(accessDatabaseFileName)) {
      try {
        db = DatabaseBuilder.open(new File(ModelManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile()));
        Table modeltable = db.getTable(Constants.MODELS_TABLE_NAME);
        modelList = modelsDataLoader.load(modeltable);
        log.debug(modelList.size() + " read from Models Data.");
        save();
      } catch (IOException e) {
        e.printStackTrace();
      }


    }
  }

  private void save() {
    System.out.println();
    int saveCount = 0;
    for (Model model :
        modelList) {
      ModelDao modelDao = new ModelDao(model);
      modelRepository.save(modelDao);
      saveCount++;
      System.out.print("\r Saving: " + saveCount);
    }
    System.out.println();
  }
}

