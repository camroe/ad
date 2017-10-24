package com.cmr.faa.DAO.ad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Constants {


    public static final String adPdfBaseUrl = "http://rgl.faa.gov/Regulatory_and_Guidance_Library/rgAD.nsf/0/";
    public static final String AD_BASE_SCHEME = "http";
    public static final String AD_HOST = "//rgl.faa.gov";
    public static final String AD_PATH = "/Regulatory_and_Guidance_Library/rgAD.nsf/0";

    @Value("${checkURLs:false}")
    private static String checkURLs = "false";

    public static boolean isCheckURLs() {
        return checkURLs.equals("true");
    }
}
