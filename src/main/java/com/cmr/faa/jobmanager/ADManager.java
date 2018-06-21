package com.cmr.faa.jobmanager;

import com.cmr.faa.AppArgs;
import com.cmr.faa.DAO.Constants;
import com.cmr.faa.DAO.ad.ADDao;
import com.cmr.faa.DAO.ad.ADDataLoader;
import com.cmr.faa.pojo.AD;
import com.cmr.faa.repositories.AdRepository;
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
public class ADManager extends Manager {
  final static Logger log = LoggerFactory.getLogger(ADManager.class);

  @Autowired
  AdRepository adRepository;

  @Autowired
  private ADDataLoader adDataLoader;


  private List<AD> adList;

  public void loadADsFromSpreadsheet(String AdSpreadSheetFileName) {
    adList = adDataLoader.load(AdSpreadSheetFileName);
    log.debug(adList.size() + " read from AD Data.");
    log.info(adDataLoader.toString());
    save();
  }


  public void loadADsFromAccessTable(String accessDatabaseFileName, AppArgs appArgs) {
    Database db;
    if (checkExistence(accessDatabaseFileName)) {
      try {
        db = DatabaseBuilder.open(new File(ADManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile()));
        Table adTable = db.getTable(Constants.ADs_TABLE_NAME);
        adList = adDataLoader.load(adTable);
        if (appArgs.isTestUrls())
          printUrlReport(adList);
        log.debug(adList.size() + " read from AD Data.");
        log.info(adDataLoader.toString());
        save();
      } catch (IOException e) {
        e.printStackTrace();
      }


    }
  }

  private void save() {
    int saveCount = 0;
    System.out.println();
    for (AD ad :
        adList) {
      ADDao adDao = new ADDao(ad);
      adRepository.save(adDao);
      saveCount++;
      System.out.print("\r Saving: " + saveCount);
    }
    System.out.println();
  }

  private void printUrlReport(List<AD> adList) {
    int adCount, validUrlAd, inValidUrlAd, noAttachements, pdfAttachments, noAttachmentsInvalid, pdfAttachementsInvalid;
    adCount = validUrlAd = inValidUrlAd = noAttachements = pdfAttachments = noAttachmentsInvalid = pdfAttachementsInvalid = 0;
    System.out.println("------ V A L I D A T I N G  U R L S ---------");
    for (AD ad :
        adList) {
      ADDao adDao = new ADDao(ad);
      adCount++;
      System.out.print("\r" + adCount + " of " + adList.size());
      adDao.validateExtendedURL();
      if ((adDao.isPdfAttachement()) & (adDao.isUrlValid())) {
        pdfAttachments++;
      } else if (adDao.isPdfAttachement()) {
        pdfAttachementsInvalid++;
        pdfAttachments++;
      } else if ((!adDao.isPdfAttachement()) & (adDao.isUrlValid())) {
        noAttachements++;
      } else if (!adDao.isPdfAttachement()) {
        noAttachements++;
        noAttachmentsInvalid++;
      }

      if (adDao.isUrlValid())
        validUrlAd++;
      else inValidUrlAd++;
    }
    System.out.println();
    StringBuilder sb = new StringBuilder();
    String lf = "\n";
    sb.append(lf)
        .append("Total ADs checked ")
        .append(adCount)
        .append(lf)
        .append("Invalid URLS = ")
        .append(inValidUrlAd)
        .append(lf)
        .append("Valid URLS = ")
        .append(validUrlAd)
        .append(lf)
        .append("PDF Attachments = ")
        .append(pdfAttachments)
        .append(lf)
        .append("Invalid PDF Attachments = ")
        .append(pdfAttachementsInvalid)
        .append(lf)
        .append("No Attachments(Just base Url) = ")
        .append(noAttachements)
        .append(lf)
        .append("Invalid base urls = ")
        .append(noAttachmentsInvalid)
        .append(lf);
    sb.trimToSize();
    log.info(sb.toString());
    System.out.println("------ E N D  V A L I D A T I N G  U R L S ---------");

  }


}


