package com.cmr.faa.DAO.Models;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.model.excel.Model;
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
public class ModelsSpreadsheetDAO {
    final static Logger log = LoggerFactory.getLogger(ModelsSpreadsheetDAO.class);
    private int modelCount = 0;

    public List<Model> load(String modelSpreadsheetFileName) {
        List<Model> modelList = new ArrayList<>();
        InputStream is = ModelsSpreadsheetDAO.class.getResourceAsStream(modelSpreadsheetFileName);
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
}

