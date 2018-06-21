package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.DAO.Mapping.ModelToADDataLoader;
import com.cmr.faa.DAO.Mapping.ModelToADMappingDAO;
import com.cmr.faa.DAO.Mapping.ModelToADMappingDBDAO;
import com.cmr.faa.pojo.ModelToAD;
import com.cmr.faa.repositories.ModelADRepository;
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
public class ModelToADMappingManager extends Manager {
  final static Logger log = LoggerFactory.getLogger(ModelToADMappingManager.class);

  @Autowired
  ModelADRepository modelADRepository;

  @Autowired
  private ModelToADMappingDBDAO modelToADMappingDBDAO;

  @Autowired
  private ModelToADDataLoader modelToADDataLoader;

  private List<ModelToAD> modelToADList;

  public void loadMappingFromSpreadsheet(String modelToADSpreadsheetFileName) {
    modelToADList = modelToADMappingDBDAO.load(modelToADSpreadsheetFileName);
    log.debug("Writing to database " + modelToADList.size() + " items");
    save();
  }

  public void loadMappingFromAccess(String accessDatabaseFileName) {
    Database db;
    if (checkExistence(accessDatabaseFileName)) {
      try {
        db = DatabaseBuilder.open(new File(ModelToADMappingManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile()));
        Table modelToADMappingTable = db.getTable(Constants.MODELS_TO_ADs_TABLE_NAME);
        modelToADList = modelToADDataLoader.load(modelToADMappingTable);
        log.debug(modelToADList.size() + " read from AD Data.");
        save();
      } catch (IOException e) {
        e.printStackTrace();
      }


    }
  }

  private void save() {
    System.out.println();
    int saveCount = 0;
    for (ModelToAD modelToAD :
        modelToADList
        ) {
      ModelToADMappingDAO modelToADMappingDAO = new ModelToADMappingDAO(modelToAD);
      modelADRepository.save(modelToADMappingDAO);
      saveCount++;
      System.out.print("\r Saving: " + saveCount);
    }
    System.out.println();
  }
}
