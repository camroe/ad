package com.cmr.faa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppArgsTest {


    String[] invalidArg;
    String[] validLoadAdsArgs;
    String[] validLoadMakeArgs;
    String[] validLoadModelArgs;
    String[] validLoadAllArgs;
    String[] validLoadAllIndividualArgs;


    @Before
    public void setUp() throws Exception {
        invalidArg = new String[]{"This is an invalid argument string"};
        validLoadAdsArgs = new String[]{"-ads"};
        validLoadMakeArgs = new String[]{"-makes"};
        validLoadModelArgs = new String[]{"-models"};
        validLoadAllArgs = new String[]{"-all"};
        validLoadAllIndividualArgs = new String[]{"-ads", "-makes", "-models"};
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValid() throws Exception {
        AppArgs appArgs = new AppArgs(invalidArg);
        assertFalse("Should be invalid with these arguments " + Arrays.toString(invalidArg),
                appArgs.isValid());
        appArgs = new AppArgs(validLoadAdsArgs);
        assertTrue("Should be valid with this argument " + Arrays.toString(validLoadAdsArgs),
                appArgs.isValid());
    }


    @Test
    public void isLoadADs() throws Exception {
        AppArgs appArgs = new AppArgs(validLoadAdsArgs);
        assertTrue("Load ADS should be true", appArgs.isLoadADs());
        assertTrue("Args should be valid for this arg array " + validLoadAdsArgs, appArgs.isValid());
    }

    @Test
    public void isLoadMakes() throws Exception {
        AppArgs appArgs = new AppArgs(validLoadMakeArgs);
        assertTrue("Load 'makes' should be true", appArgs.isLoadMakes());
        assertTrue("Args should be valid for this arg array " + validLoadMakeArgs, appArgs.isValid());
    }

    @Test
    public void isLoadModels() throws Exception {
        AppArgs appArgs = new AppArgs(validLoadModelArgs);
        assertTrue("Load 'models' should be true", appArgs.isLoadModels());
        assertTrue("Args should be valid for this arg array " + validLoadModelArgs, appArgs.isValid());
    }

    @Test
    public void testAllFlag() throws Exception {
        AppArgs appArgs = new AppArgs(validLoadAllArgs);
        assertTrue("Load ADS should be true", appArgs.isLoadADs());
        assertTrue("Load 'makes' should be true", appArgs.isLoadMakes());
        assertTrue("Load 'models' should be true", appArgs.isLoadModels());
        assertTrue("Args should be valid for this arg array " + validLoadModelArgs, appArgs.isValid());
        appArgs = new AppArgs(validLoadAllIndividualArgs);
        assertTrue("Load ADS should be true", appArgs.isLoadADs());
        assertTrue("Load 'makes' should be true", appArgs.isLoadMakes());
        assertTrue("Load 'models' should be true", appArgs.isLoadModels());
        assertTrue("Args should be valid for this arg array " + validLoadModelArgs, appArgs.isValid());
    }


}

