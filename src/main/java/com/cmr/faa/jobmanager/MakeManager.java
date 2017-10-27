package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.makes.MakeDao;
import com.cmr.faa.DAO.makes.MakesSpreadsheetDAO;
import com.cmr.faa.model.excel.Make;
import com.cmr.faa.repositories.MakeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MakeManager {
    final static Logger log = LoggerFactory.getLogger(MakeManager.class);

    @Autowired
    MakeRepository makeRepository;

    @Autowired
    private MakesSpreadsheetDAO makesSpreadsheetDAO;
    private List<Make> makeList;

    public void loadMakesFromSpreadsheet(String makeSpreadSheetFilename) {
        makeList = makesSpreadsheetDAO.load(makeSpreadSheetFilename);
        log.debug(makeList.size() + " Read from Makes Spreadsheet");
        for (Make make :
                makeList) {
            MakeDao makeDao = new MakeDao(make);
            makeRepository.save(makeDao);
        }
    }
}
