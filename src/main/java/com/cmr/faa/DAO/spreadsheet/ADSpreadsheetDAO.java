package com.cmr.faa.DAO.spreadsheet;

import com.cmr.faa.model.excel.AD;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class ADSpreadsheetDAO {
    final static Logger log = LoggerFactory.getLogger(ADSpreadsheetDAO.class);

    private int adcount, gliderCount, largeAirPlaneCount, smallAirplaneCount, ballonCount, rotorcraftCount, aircraftCount, engineCount, applianceCount, propellerCount, smallLargeAirplaneCount;
    private Set<String> productTypeSet = new HashSet<>();
    private Set<String> productSubTypeSet = new HashSet<>();

    public List<AD> load(String adSpreadSheetFileName) {
        List<AD> adList = new ArrayList<>();

        //init counters
        adcount = gliderCount = largeAirPlaneCount = smallAirplaneCount = ballonCount = rotorcraftCount = aircraftCount = engineCount = applianceCount = propellerCount = smallLargeAirplaneCount = 0;
        InputStream is = ADSpreadsheetDAO.class.getResourceAsStream(adSpreadSheetFileName);
        log.debug(String.valueOf(is));
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet ADSheet = wb.getSheetAt(0);
            Iterator<Row> iterator = ADSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0)
                    continue; // skip heading row

                adcount++;
                AD ad = new AD();
                Cell cell = currentRow.getCell(Constants.AD_ID_POS);
                ad.setAd_id(new Integer((int) cell.getNumericCellValue()));
                cell = currentRow.getCell(Constants.AD_NUMBER_POS);
                ad.setAdNumber(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.DOCKET_NUMER_POS);
                ad.setDocketNumber(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.AMENDMENT_NUMBER_POS);
                ad.setAmendmentNumber(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.PUBLISH_DATE_POS);
                if (isNotBlank(cell))
                    ad.setPublishDate(cell.getDateCellValue());
                cell = currentRow.getCell(Constants.ISSUE_DATE_POS);
                if (isNotBlank(cell))
                    ad.setIssueDate(cell.getDateCellValue());
                cell = currentRow.getCell(Constants.EFFECTIVE_DATE_POS);
                if (isNotBlank(cell))
                    ad.setEffectiveDate(cell.getDateCellValue());
                cell = currentRow.getCell(Constants.SUPERSEDES_POS);
                ad.setSupersedes(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.SUPERSEDED_BY_POS);
                ad.setSupersededBy(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.SUBJECT_POS_POS);
                ad.setSubject(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.PRODUCT_TYPE_POS);
                ad.setProductType(cell.getStringCellValue());
                adjustProductTypeCounts(ad);
                cell = currentRow.getCell(Constants.PRODUCT_SUBTYPE_POS);
                ad.setProductSubtype(cell.getStringCellValue());
                adjustProductSubTypeCounts(ad);
                cell = currentRow.getCell(Constants.UNID_POS);
                ad.setUNID(cell.getStringCellValue());
                cell = currentRow.getCell(Constants.ATTACHMENTS_POS);
                ad.setAttachements(cell.getStringCellValue());
                adList.add(ad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(adcount + " lines read from " + adSpreadSheetFileName);
        return adList;
    }


    public int getAdcount() {
        return adcount;
    }

    public int getGliderCount() {
        return gliderCount;
    }

    public int getLargeAirPlaneCount() {
        return largeAirPlaneCount;
    }

    public int getSmallAirplaneCount() {
        return smallAirplaneCount;
    }

    public int getBallonCount() {
        return ballonCount;
    }

    public int getRotorcraftCount() {
        return rotorcraftCount;
    }

    public int getAircraftCount() {
        return aircraftCount;
    }

    public int getEngineCount() {
        return engineCount;
    }

    public int getApplianceCount() {
        return applianceCount;
    }

    public Set<String> getProductTypeSet() {
        return productTypeSet;
    }

    public Set<String> getProductSubTypeSet() {
        return productSubTypeSet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lf = "\n";
        String tab = "\t";
        sb.append(lf)
                .append("------L O A D  S U M M A R Y ------")
                .append(lf)
                .append("ADs: ")
                .append(this.adcount)
                .append(lf)
                .append("AirCraft ADs: ")
                .append(this.aircraftCount)

                .append(lf)
                .append("Engine Ads: ")
                .append(this.engineCount)
                .append(lf)
                .append("Propeller Ads: ")
                .append(this.propellerCount)
                .append(lf)
                .append("Appliance ADs: ")
                .append(this.applianceCount)
                .append(lf)
                .append(lf)
                .append(tab)
                .append("Small Airplane ADs ")
                .append(this.smallAirplaneCount)
                .append(lf)
                .append(tab)
                .append("Large Airplane ADs ")
                .append(this.largeAirPlaneCount)
                .append(lf)
                .append(tab)
                .append("Large/Small Airplane ADs ")
                .append(this.smallLargeAirplaneCount)
                .append(lf)
                .append(tab)
                .append("Rotorcraft ADs ")
                .append(this.rotorcraftCount)
                .append(lf)
                .append(tab)
                .append("Glider ADs ")
                .append(this.gliderCount)
                .append(lf)
                .append(tab)
                .append("Ballon ADs ")
                .append(this.ballonCount)
                .append(lf)
                .append(lf);
        sb.append("P R O D U C T   T Y P E S ")
                .append(lf);
        for (String productType :
                this.productTypeSet) {
            sb.append(productType)
                    .append(lf);
        }
        sb.append(lf)
                .append("P R O D U C T  S U B T Y P E S ")
                .append(lf);
        for (String productSubType :
                this.productSubTypeSet) {
            sb.append(productSubType)
                    .append(lf);
        }
        return sb.toString();
    }

    private void adjustProductTypeCounts(AD ad) {
        String productType = ad.getProductType();
        if ((null == productType) || (productType.equals(""))) {
            log.warn("BLANK product type in spreadsheet. AD_ID => " + ad.getAd_id()
                    + "  AD=> "
                    + ad.getAdNumber());
            productType = applySpecialRulesProductType(ad);
        }
        if (productType.equals(Constants.PRODUCT_TYPE_AIRCRAFT))
            aircraftCount++;
        if (productType.equals(Constants.PRODUCT_TYPE_APPLIANCE))
            applianceCount++;
        if (productType.equals(Constants.PRODUCT_TYPE_ENGINE))
            engineCount++;
        if (productType.equals(Constants.PRODUCT_TYPE_PROPELLER))
            propellerCount++;

        if (!productType.equals(""))
            productTypeSet.add(productType);
    }


    private void adjustProductSubTypeCounts(AD ad) {
        String productType = ad.getProductType();
        String productSubType = ad.getProductSubtype();
        if ((null == productType) || (productType.equals("")))
            return;

        if (productType.equals(Constants.PRODUCT_TYPE_AIRCRAFT)) {

            if ((null == productSubType) || (productSubType.equals(""))) {
                log.warn("BLANK product SUB_type in spreadsheet. AD_ID => " + ad.getAd_id()
                        + "  AD=> "
                        + ad.getAdNumber());
                productSubType = applySpecialRulesProductSubType(ad);
            }
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_GLIDER))
                gliderCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE))
                largeAirPlaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_ROTOCRAFT))
                rotorcraftCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_SMALL_AIRPLANE))
                smallAirplaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_SMALL_LARGE_AIRPLANE))
                smallLargeAirplaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_BALLOON))
                ballonCount++;
            productSubTypeSet.add(productSubType);
        } else if ((productType.equals(Constants.PRODUCT_TYPE_ENGINE))
                || (productType.equals(Constants.PRODUCT_TYPE_PROPELLER))
                || (productType.equals(Constants.PRODUCT_TYPE_APPLIANCE))) {
            //Engine,Propeller are inconsistent in their application of Subtypes
            if ((null == productSubType) || (productSubType.equals(""))) {
                productSubType = "";
            }
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_GLIDER))
                gliderCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE))
                largeAirPlaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_ROTOCRAFT))
                rotorcraftCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_SMALL_AIRPLANE))
                smallAirplaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_SMALL_LARGE_AIRPLANE))
                smallLargeAirplaneCount++;
            if (productSubType.equals(Constants.PRODUCT_SUBTYPE_BALLOON))
                ballonCount++;
            if (!productSubType.equals(""))
                productSubTypeSet.add(productSubType);


        } else
            log.warn("Unexpected Subtype found. AD_ID => " + ad.getAd_id()
                    + "  AD=> "
                    + ad.getAdNumber()
                    + "  ProductSubType => "
                    + productSubType);

    }

    private String applySpecialRulesProductType(AD ad) {

        if ((ad.getAdNumber().equals("2002-14-51"))
                || (ad.getAdNumber().equals("2010-25-51"))) {
            ad.setProductType(Constants.PRODUCT_TYPE_AIRCRAFT);
            log.warn("Product Type changed to " + Constants.PRODUCT_TYPE_AIRCRAFT);
            return Constants.PRODUCT_TYPE_AIRCRAFT;
        }
        //Superseded by AD 91-04-02 which was an Engine Product type.
        if ((ad.getAdNumber().equals("90-21-01"))) {
            ad.setProductType(Constants.PRODUCT_TYPE_ENGINE);
            log.warn("Product Type Changed to " + Constants.PRODUCT_TYPE_ENGINE);
            return Constants.PRODUCT_TYPE_ENGINE;
        }
        log.warn("Product type Blank - no change");
        return "";
    }

    private String applySpecialRulesProductSubType(AD ad) {
        //Main Rotor blades Ad
        if ((ad.getAdNumber().equals("2002-14-51"))
                || (ad.getAdNumber().equals("2010-25-51"))) {
            ad.setProductSubtype(Constants.PRODUCT_SUBTYPE_ROTOCRAFT);
            log.warn("Product Sub-Type changed to " + Constants.PRODUCT_SUBTYPE_ROTOCRAFT);
            return Constants.PRODUCT_SUBTYPE_ROTOCRAFT;
        }
        //Airbus wings Ad.
        if (ad.getAdNumber().equals("2014-14-05")) {
            ad.setProductSubtype(Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE);
            log.warn("Product Sub-Type changed to " + Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE);
            return Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE;
        }

        log.warn("Product Sub Type Blank - no change");
        return "";
    }


    private boolean isNotBlank(Cell cell) {
        return (cell != null && cell.getCellTypeEnum() != CellType.BLANK);
    }

}
