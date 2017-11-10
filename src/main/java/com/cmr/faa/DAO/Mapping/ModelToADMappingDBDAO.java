package com.cmr.faa.DAO.Mapping;

import com.cmr.faa.DAO.makes.MakesSpreadsheetDAO;
import com.cmr.faa.model.access.ModelToAD;
import com.cmr.faa.model.excel.Make;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelToADMappingDBDAO {
    final static Logger log = LoggerFactory.getLogger(ModelToADMappingDBDAO.class);

    public List<ModelToAD> load(String accessDatabaseFileName) {
        List<ModelToAD> modelToADList = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(accessDatabaseFileName).getFile());
            Table table = DatabaseBuilder.open(file).getTable("Models_to_ADs");
            for (Row row : table
                    ) {
                System.out.println(row.get("Model_ID") + ":" + row.get("AD_ID"));
                modelToADList.add(new ModelToAD(row.getInt("Model_ID"),row.getInt("AD_ID")));
            }
        }
        catch (IOException e) {
            log.error("Could not read file " + accessDatabaseFileName);
            e.printStackTrace();
        }
    return modelToADList;
    }
//    public List<Make> load(String makeSpreadsheetFileName) {
//        List<Make> makeList = new ArrayList<>();
//        InputStream is = MakesSpreadsheetDAO.class.getResourceAsStream(makeSpreadsheetFileName);
}
