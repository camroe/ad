package com.cmr.faa.DAO.makes;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.model.excel.Make;
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
public class MakesSpreadsheetDAO {
    final static Logger log = LoggerFactory.getLogger(MakesSpreadsheetDAO.class);

    private int makeCount = 0;

    public List<Make> load(String makeSpreadsheetFileName) {
        List<Make> makeList = new ArrayList<>();
        InputStream is = MakesSpreadsheetDAO.class.getResourceAsStream(makeSpreadsheetFileName);
        log.debug(String.valueOf(is));
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet ADSheet = wb.getSheetAt(0);
            Iterator<Row> iterator = ADSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0)
                    continue; // skip heading row

                makeCount++;
                Make make = new Make();
                Cell cell = currentRow.getCell(Constants.MAKE_ID_POS);
                make.setMake_id(new Integer((int) cell.getNumericCellValue()));
                cell = currentRow.getCell(Constants.MAKE_MAKE_POS);
                make.setMake(cell.getStringCellValue());
                makeList.add(make);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(makeCount + " lines read from " + makeSpreadsheetFileName);
        return makeList;
    }
}
