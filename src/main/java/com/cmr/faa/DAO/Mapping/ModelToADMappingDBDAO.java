package com.cmr.faa.DAO.Mapping;

import com.cmr.faa.DAO.makes.MakesDataLoader;
import com.cmr.faa.pojo.ModelToAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ModelToADMappingDBDAO {
    final static Logger log = LoggerFactory.getLogger(ModelToADMappingDBDAO.class);

    public List<ModelToAD> load(String modelToADSpreadsheetFileName) {
        List<ModelToAD> modelToADList = new ArrayList<>();
        InputStream is = MakesDataLoader.class.getResourceAsStream(modelToADSpreadsheetFileName);
        return Collections.emptyList();
    }
}
