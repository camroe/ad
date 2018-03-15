package com.cmr.faa.DAO.Mapping;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.DAO.makes.MakesDataLoader;
import com.cmr.faa.pojo.ModelToAD;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ModelToADDataLoader {
    public List<ModelToAD> load(String modelToADSpreadsheetFileName) {
        List<ModelToAD> modelToADList = new ArrayList<>();
        InputStream is = MakesDataLoader.class.getResourceAsStream(modelToADSpreadsheetFileName);
        //TODO: Do the real processing here for the spreadsheet.
        return Collections.emptyList();
    }

    public List<ModelToAD> load(Table modelToADTable) {
        int modelToADCount = 0;
        List<ModelToAD> modelToADList = new ArrayList<>();
        ModelToAD modelToAD;
        System.out.println("----B E G I N  L  O A D -----------");
        for (Row row :
                modelToADTable) {
            modelToADCount++;
            System.out.print("\r Loading: " + modelToADCount);
            modelToAD = new ModelToAD();
            modelToAD.setAd_id(new Integer(row.getInt(Constants.AD_ID_COLUMN_NAME)));
            modelToAD.setModel_id(new Integer(row.getInt(Constants.MODEL_ID_COLUMN_NAME)));
            modelToADList.add(modelToAD);
        }
        System.out.println();
        System.out.println("-----E N D  L O A D----------");
        return modelToADList;
    }
}
