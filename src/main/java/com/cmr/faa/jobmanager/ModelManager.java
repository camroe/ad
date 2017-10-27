package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.Models.ModelDao;
import com.cmr.faa.DAO.Models.ModelsSpreadsheetDAO;
import com.cmr.faa.model.excel.Model;
import com.cmr.faa.repositories.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelManager {
    final static Logger log = LoggerFactory.getLogger(ModelManager.class);

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    private ModelsSpreadsheetDAO modelsSpreadsheetDAO;
    private List<Model> modelList;

    public void loadModelsFromSpreadsheet(String modelSpreadsheetFileName) {
        modelList = modelsSpreadsheetDAO.load(modelSpreadsheetFileName);
        log.debug(modelList.size() + " Read from Models Spreadsheet");
        for (Model model :
                modelList) {
            ModelDao modelDao = new ModelDao(model);
            modelRepository.save(modelDao);
        }
    }
}

