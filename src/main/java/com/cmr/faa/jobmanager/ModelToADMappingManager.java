package com.cmr.faa.jobmanager;

import com.cmr.faa.DAO.Mapping.ModelToADMappingDAO;
import com.cmr.faa.DAO.Mapping.ModelToADMappingDBDAO;
import com.cmr.faa.model.access.ModelToAD;
import com.cmr.faa.repositories.ModelADRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelToADMappingManager {
final static Logger log = LoggerFactory.getLogger(ModelToADMappingManager.class);
    @Autowired
    private ModelToADMappingDBDAO modelToADMappingDBDAO;

    @Autowired
    ModelADRepository modelADRepository;
    private List<ModelToAD>  modelToADList;

    public void loadMapping(String accessDatabaseFileName) {
modelToADList = modelToADMappingDBDAO.load(accessDatabaseFileName);
log.debug(modelToADList.size() + " Read from Model To AD mapping)");
        for (ModelToAD modelToAD :
                modelToADList
             ) {
            ModelToADMappingDAO modelToADMappingDAO = new ModelToADMappingDAO(modelToAD);
            modelADRepository.save(modelToADMappingDAO);
        }
    }
}
