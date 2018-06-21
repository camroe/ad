package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.DAO.makes.MakeDao;
import com.cmr.faa.DAO.makes.MakesDataLoader;
import com.cmr.faa.pojo.Make;
import com.cmr.faa.repositories.MakeRepository;
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
public class MakeManager extends Manager {
  final static Logger log = LoggerFactory.getLogger(MakeManager.class);

  @Autowired
  MakeRepository makeRepository;

  @Autowired
  private MakesDataLoader makesDataLoader;
  private List<Make> makeList;

  public void loadMakesFromSpreadsheet(String makeSpreadSheetFilename) {
    makeList = makesDataLoader.loadSpreadSheet(makeSpreadSheetFilename);
    log.debug(makeList.size() + " Read from Makes Spreadsheet");
    save();
  }

  private void save() {
    System.out.println();
    int saveCount = 0;
    for (Make make :
        makeList) {
      MakeDao makeDao = new MakeDao(make);
      makeRepository.save(makeDao);
      saveCount++;
      System.out.print("\r Saving: " + saveCount);
    }
    System.out.println();
  }

  public void loadMakesFromAccessTable(String accessDatabaseFileName) {
    Database db;
    if (checkExistence(accessDatabaseFileName)) {
      try {
        db = DatabaseBuilder.open(new File(MakeManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile()));
        Table makesTable = db.getTable(Constants.MAKES_TABLE_NAME);
        makeList = makesDataLoader.load(makesTable);
        log.debug(makeList.size() + " read from Makes Data.");
        save();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
