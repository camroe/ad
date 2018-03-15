package com.cmr.faa.DAO.ad;

import com.cmr.faa.DAO.Constants;
import com.cmr.faa.pojo.AD;
import com.healthmarketscience.jackcess.Table;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class ADDataLoader {
    final static Logger log = LoggerFactory.getLogger(ADDataLoader.class);

    private int adcount, gliderCount, largeAirPlaneCount, smallAirplaneCount, ballonCount, rotorcraftCount, aircraftCount, engineCount, applianceCount, propellerCount, smallLargeAirplaneCount;
    private Set<String> productTypeSet = new HashSet<>();
    private Set<String> productSubTypeSet = new HashSet<>();

    public List<AD> load(Table adTable) {
        List<AD> adList = new ArrayList<>();
        initCounters();
//        try {
//            adTable.getNextRow();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("----B E G I N  L  O A D -----------");
        for (com.healthmarketscience.jackcess.Row row :
                adTable) {
            adcount++;
            System.out.print("\r Loading: " + adcount);
            AD ad = new AD();
            ad.setAd_id(new Integer(row.getInt(Constants.AD_ID_COLUMN_NAME)));
            ad.setAdNumber(row.getString(Constants.AD_NUMBER_COLUMN_NAME));
            ad.setDocketNumber(row.getString(Constants.DOCKET_NUMBER_COLUMN_NAME));
            ad.setAmendmentNumber(row.getString(Constants.AMMENDMENT_NUMBER_COLUMN_NAME));
            ad.setPublishDate(row.getDate(Constants.PUBLISH_DATE_COLUMN_NAME));
            ad.setIssueDate(row.getDate(Constants.ISSUE_DATE_COLUMN_NAME));
            ad.setEffectiveDate(row.getDate(Constants.EFFECTIVE_DATE_COLUMN_NAME));
            ad.setSupersedes(row.getString(Constants.SUPERSEDES__COLUMN_NAME));
            ad.setSupersededBy(row.getString(Constants.SUPERSEDED_BY_COLUMN_NAME));
            ad.setSubject(row.getString(Constants.SUBJECT_COLUMN_NAME));
            ad.setProductType(row.getString(Constants.PRODUCT_TYPE_COLUMN_NAME));
            adjustProductTypeCounts(ad);
            ad.setProductSubtype(row.getString(Constants.PRODUCT_SUBTYPE_COLUMN_NAME));
            adjustProductSubTypeCounts(ad);
            ad.setUNID(row.getString(Constants.UNID_COLUMN_NAME));
            ad.setAttachements(row.getString(Constants.ATTACHMENTS_COLUMN_NAME));
            adList.add(ad);
        }
        System.out.println();
        System.out.println("-----E N D  L O A D----------");
        return adList;
    }


    public List<AD> load(String adSpreadSheetFileName) {
        List<AD> adList = new ArrayList<>();
        initCounters();
        InputStream is = ADDataLoader.class.getResourceAsStream(adSpreadSheetFileName);
        log.debug(String.valueOf(is) + " " + adSpreadSheetFileName);
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet ADSheet = wb.getSheetAt(0);
            Iterator<Row> iterator = ADSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0)
                    continue; // skip heading row

                adcount++;
                System.out.print("\r" + adcount);
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

    private void initCounters() {
        adcount = gliderCount = largeAirPlaneCount = smallAirplaneCount = ballonCount = rotorcraftCount = aircraftCount = engineCount = applianceCount = propellerCount = smallLargeAirplaneCount = 0;
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
            System.out.println();
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
                System.out.println();
                log.warn("For AD: \n" + ad.getAdNumber() + "\nBLANK product SUB_type in spreadsheet. AD_ID => " + ad.getAd_id()
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


        } else {
            System.out.println();
            log.warn("Unexpected Subtype found. AD_ID => " + ad.getAd_id()
                    + "  AD=> "
                    + ad.getAdNumber()
                    + "  ProductSubType => "
                    + productSubType);
        }
    }

    private String applySpecialRulesProductType(AD ad) {

        if ((ad.getAdNumber().equals("2002-14-51"))
                || (ad.getAdNumber().equals("2010-25-51"))) {
            log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct Type changed to " + Constants.PRODUCT_TYPE_AIRCRAFT
                    + " from <" + ad.getProductType() + ">");
            ad.setProductType(Constants.PRODUCT_TYPE_AIRCRAFT);
            System.out.println();
            return Constants.PRODUCT_TYPE_AIRCRAFT;
        }
        //Superseded by AD 91-04-02 which was an Engine Product type.
        if ((ad.getAdNumber().equals("90-21-01"))) {
            log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct Type Changed to " + Constants.PRODUCT_TYPE_ENGINE
                    + " from <" + ad.getProductType() + ">");
            ad.setProductType(Constants.PRODUCT_TYPE_ENGINE);
            System.out.println();
            return Constants.PRODUCT_TYPE_ENGINE;
        }
        System.out.println();
        log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct type Blank - no change");
        return "";
    }

    private String applySpecialRulesProductSubType(AD ad) {
        //Main Rotor blades Ad
        if ((ad.getAdNumber().equals("2002-14-51"))
                || (ad.getAdNumber().equals("2010-25-51"))) {
            log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct Sub-Type changed to " + Constants.PRODUCT_SUBTYPE_ROTOCRAFT
                    + " from <" + ad.getProductSubtype() + ">");
            ad.setProductSubtype(Constants.PRODUCT_SUBTYPE_ROTOCRAFT);
            System.out.println();
            return Constants.PRODUCT_SUBTYPE_ROTOCRAFT;
        }
        //Airbus wings Ad.
        if (ad.getAdNumber().equals("2014-14-05")) {
            log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct Sub-Type changed to " + Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE
                    + " from <" + ad.getProductSubtype() + ">");
            ad.setProductSubtype(Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE);
            System.out.println();
            return Constants.PRODUCT_SUBTYPE_LARGE_AIRPLANE;
        }
        System.out.println();
        log.warn("For AD: \n" + ad.getAdNumber() + "\nProduct Sub Type Blank - no change");
        return "";
    }


    private boolean isNotBlank(Cell cell) {
        return (cell != null && cell.getCellTypeEnum() != CellType.BLANK);
    }


}
