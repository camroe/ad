package com.cmr.faa.DAO.Models;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.pojo.Model;
import com.healthmarketscience.jackcess.Table;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ModelsDataLoader {
    final static Logger log = LoggerFactory.getLogger(ModelsDataLoader.class);
    private int modelCount = 0;

    public List<Model> load(String modelSpreadsheetFileName) {
        List<Model> modelList = new ArrayList<>();
        InputStream is = ModelsDataLoader.class.getResourceAsStream(modelSpreadsheetFileName);
        log.debug(String.valueOf(is));
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet ADSheet = wb.getSheetAt(0);
            Iterator<Row> iterator = ADSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0)
                    continue; // skip heading row

                modelCount++;
                Model model = new Model();
                Cell cell = currentRow.getCell(Constants.MODEL_ID_POS);
                model.setModel_id(new Integer((int) cell.getNumericCellValue()));
                cell = currentRow.getCell(Constants.MODEL_MAKE_ID_POS);
                model.setMake_id(new Integer((int) cell.getNumericCellValue()));
                cell = currentRow.getCell(Constants.MODEL_NAME_POS);
                model.setModel(cell.getStringCellValue());
                modelList.add(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(modelCount + " lines read from " + modelSpreadsheetFileName);
        return modelList;
    }

    public List<Model> load(Table modeltable) {
        modelCount = 0;
        List<Model> modelList = new ArrayList<>();
        Model model;
        System.out.println("----B E G I N  L  O A D -----------");
        for (com.healthmarketscience.jackcess.Row row :
                modeltable) {
            modelCount++;
            System.out.print("\r Loading: " + modelCount);
            model = new Model();
            model.setModel_id(new Integer(row.getInt(Constants.MODEL_ID_COLUMN_NAME)));
            model.setMake_id(new Integer(row.getInt(Constants.MAKE_ID_COLUMN_NAME)));
            validateMakeIDExists(model.getMake_id());
            model.setModel(row.getString(Constants.MODEL_COLUMN_NAME));

            modelList.add(model);
        }
        System.out.println();
        System.out.println("-----E N D  L O A D----------");

        return modelList;
    }

    private void validateMakeIDExists(Integer make_id) {
        //TODO: Validate that the Make_ID exists, Log any missing.
    }
}

