package com.cmr.faa.DAO;

public class Constants {

    public static final String PRODUCT_TYPE_APPLIANCE = "Appliance";
    public static final String PRODUCT_TYPE_PROPELLER = "Propeller";
    public static final String PRODUCT_TYPE_AIRCRAFT = "Aircraft";
    public static final String PRODUCT_TYPE_ENGINE = "Engine";
    public static final String PRODUCT_SUBTYPE_LARGE_AIRPLANE = "Large Airplane";
    public static final String PRODUCT_SUBTYPE_SMALL_AIRPLANE = "Small Airplane";
    public static final String PRODUCT_SUBTYPE_ROTOCRAFT = "Rotorcraft";
    public static final String PRODUCT_SUBTYPE_GLIDER = "Glider";
    public static final String PRODUCT_SUBTYPE_SMALL_LARGE_AIRPLANE = "Small/Large Airplane";
    public static final String PRODUCT_SUBTYPE_BALLOON = "Balloon";


    public static final int AD_ID_POS = 0;
    public static final int AD_NUMBER_POS = 1;
    public static final int DOCKET_NUMER_POS = 2;
    public static final int AMENDMENT_NUMBER_POS = 3;
    public static final int PUBLISH_DATE_POS = 4;
    public static final int ISSUE_DATE_POS = 5;
    public static final int EFFECTIVE_DATE_POS = 6;
    public static final int SUPERSEDES_POS = 7;
    public static final int SUPERSEDED_BY_POS = 8;
    public static final int SUBJECT_POS_POS = 9;
    public static final int PRODUCT_TYPE_POS = 10;
    public static final int PRODUCT_SUBTYPE_POS = 11;
    public static final int UNID_POS = 12;
    public static final int ATTACHMENTS_POS = 13;

    public static final int MAKE_ID_POS = 0;
    public static final int MAKE_MAKE_POS = 1;

    public static final String ADs_TABLE_NAME = "ADs";
    public static final int MODEL_ID_POS = 0;
    public static final int MODEL_MAKE_ID_POS = 1;
    public static final int MODEL_NAME_POS = 2;
    public static final String AD_ID_COLUMN_NAME = "AD_ID";
    public static final String AD_NUMBER_COLUMN_NAME = "ADNumber";
    public static final String DOCKET_NUMBER_COLUMN_NAME = "DocketNumber";
    public static final String AMMENDMENT_NUMBER_COLUMN_NAME = "AmmendmentNumber";
    public static final String PUBLISH_DATE_COLUMN_NAME = "PublishDate";
    public static final String ISSUE_DATE_COLUMN_NAME = "IssueDate";
    public static final String EFFECTIVE_DATE_COLUMN_NAME = "EffectiveDate";
    public static final String SUPERSEDES__COLUMN_NAME = "Supersedes";
    public static final String SUPERSEDED_BY_COLUMN_NAME = "SupersededBy";
    public static final String SUBJECT_COLUMN_NAME = "Subject";
    public static final String PRODUCT_TYPE_COLUMN_NAME = "ProductType";
    public static final String PRODUCT_SUBTYPE_COLUMN_NAME = "ProductSubtype";
    public static final String UNID_COLUMN_NAME = "UNID";
    public static final String ATTACHMENTS_COLUMN_NAME = "Attachments";

    public static final String MAKES_TABLE_NAME = "Makes";
    public static final String MAKE_ID_COLUMN_NAME = "Make_ID";
    public static final String MAKE_COLUMN_NAME = "Make";

    public static final String MODELS_TABLE_NAME = "Models";
    public static final String MODEL_ID_COLUMN_NAME = "Model_ID";
    public static final String MODEL_COLUMN_NAME = "Model";

    public static String MODELS_TO_ADs_TABLE_NAME = "Models_to_ADs";
}
