package com.cmr.faa.DAO.makes;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.pojo.Make;
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
public class MakesDataLoader {
    final static Logger log = LoggerFactory.getLogger(MakesDataLoader.class);

    private int makeCount = 0;

    public List<Make> loadSpreadSheet(String makeSpreadsheetFileName) {
        makeCount = 0;
        List<Make> makeList = new ArrayList<>();
        InputStream is = MakesDataLoader.class.getResourceAsStream(makeSpreadsheetFileName);
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

    public List<Make> load(Table makesTable) {
        makeCount = 0;
        List<Make> makeList = new ArrayList<>();
        Make make;
        System.out.println("----B E G I N  L  O A D -----------");
        for (com.healthmarketscience.jackcess.Row row :
                makesTable) {
            makeCount++;
            System.out.print("\r Loading: " + makeCount);
            make = new Make();
            make.setMake_id(new Integer(row.getInt(Constants.MAKE_ID_COLUMN_NAME)));
            make.setMake(row.getString(Constants.MAKE_COLUMN_NAME));
            makeList.add(make);
        }
        System.out.println();
        System.out.println("-----E N D  L O A D----------");
        return makeList;
    }
}
