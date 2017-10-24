package com.cmr.faa.jobmanager;

import com.cmr.faa.model.excel.AD;
import com.cmr.faa.DAO.spreadsheet.ADSpreadsheetDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ADManager {
    final static Logger log = LoggerFactory.getLogger(ADManager.class);

    @Autowired
    private ADSpreadsheetDAO adSpreadsheetDAO;

    private List<AD> adList;

    public void load() {
        adList = adSpreadsheetDAO.load();
        log.debug(adList.size() + " read from AD Spreadsheet.");
        log.info(adSpreadsheetDAO.toString());
    }


}
