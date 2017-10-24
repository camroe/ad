package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.ad.ADDao;
import com.cmr.faa.DAO.ad.Constants;
import com.cmr.faa.DAO.spreadsheet.ADSpreadsheetDAO;
import com.cmr.faa.model.excel.AD;
import com.cmr.faa.repositories.AdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ADManager {
    final static Logger log = LoggerFactory.getLogger(ADManager.class);
    @Autowired
    AdRepository adRepository;
    @Autowired
    private ADSpreadsheetDAO adSpreadsheetDAO;
    private List<AD> adList;

    public void load() {
        adList = adSpreadsheetDAO.load();
        log.debug(adList.size() + " read from AD Spreadsheet.");
        log.info(adSpreadsheetDAO.toString());
        if (Constants.isCheckURLs()) {
            log.info("Checking URLS");
            printUrlReport(adList);
        }
        log.info("Saving ADs");
        for (AD ad :
                adList) {
            ADDao adDao = new ADDao(ad);
            adRepository.save(adDao);
        }
    }


    private void printUrlReport(List<AD> adList) {
        int adCount, validUrlAd, inValidUrlAd, noAttachements, pdfAttachments, noAttachmentsInvalid, pdfAttachementsInvalid;
        adCount = validUrlAd = inValidUrlAd = noAttachements = pdfAttachments = noAttachmentsInvalid = pdfAttachementsInvalid = 0;

        for (AD ad :
                adList) {
            ADDao adDao = new ADDao(ad);
            adCount++;
            System.out.print("\r" + adCount + " of " + adList.size());
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
    }


}
