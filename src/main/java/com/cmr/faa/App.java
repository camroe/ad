package com.cmr.faa;


import com.cmr.faa.jobmanager.ADManager;
import com.cmr.faa.jobmanager.MakeManager;
import com.cmr.faa.jobmanager.ModelManager;
import com.cmr.faa.jobmanager.ModelToADMappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class App implements CommandLineRunner {
  final static Logger log = LoggerFactory.getLogger(App.class);

  @Value("${AdSpreadSheetFileName:unknown}")
  private String AdSpreadSheetFileName;

  @Value("${MakeSpreadSheetFileName:unknown}")
  private String MakeSpreadSheetFileName;

  @Value("${ModelSpreadSheetFileName:unknown}")
  private String ModelSpreadSheetFileName;

  @Value("${ModelToADSpreadsheetFileName:unKnown}")
  private String ModelToADSpreadsheetFileName;

  @Value("${AccessDatabaseFileName:unknown}")
  private String AccessDatabaseFileName;

  @Autowired
  private IntroMessage introMessage;

  @Autowired
  private ADManager adManager;

  @Autowired
  private MakeManager makeManager;

  @Autowired
  private ModelManager modelManager;

  @Autowired
  private ModelToADMappingManager modelToADMappingManager;

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(App.class);
    application.run(args);

  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("----------------------------------------------------------------");
    System.out.println(introMessage.getApplicationName());
    System.out.println("----------------------------------------------------------------");
    AppArgs appArgs = new AppArgs(args);
    if (!appArgs.isValid()) {
      System.out.println(appArgs.usageMessage());
      System.exit(1);
    }
    if (introMessage.isDisplaySpringBeans())
      introMessage.printBeans();
    loggerReport();
    //Real work here
    if (appArgs.isValid()) {
      if ((appArgs.isLoadADs()) && (appArgs.isSpreadSheet()))
        adManager.loadADsFromSpreadsheet(AdSpreadSheetFileName);
      if ((appArgs.isLoadMakes()) && (appArgs.isSpreadSheet()))
        makeManager.loadMakesFromSpreadsheet(MakeSpreadSheetFileName);
      if ((appArgs.isLoadModels()) && (appArgs.isSpreadSheet()))
        modelManager.loadModelsFromSpreadsheet(ModelSpreadSheetFileName);
      if ((appArgs.isLoadMapping()) && (appArgs.isSpreadSheet()))
        modelToADMappingManager.loadMappingFromSpreadsheet(ModelToADSpreadsheetFileName);

      if ((appArgs.isDatabase()) && appArgs.isDescribe())
        adManager.describeDatabase(AccessDatabaseFileName);

      if ((appArgs.isLoadADs()) && (appArgs.isDatabase()))
        adManager.loadADsFromAccessTable(AccessDatabaseFileName, appArgs);
      if ((appArgs.isLoadMakes()) && (appArgs.isDatabase()))
        makeManager.loadMakesFromAccessTable(AccessDatabaseFileName);
      if ((appArgs.isLoadModels()) && (appArgs.isDatabase()))
        modelManager.loadModelsFromAccess(AccessDatabaseFileName);
      if ((appArgs.isLoadMapping()) && (appArgs.isDatabase()))
        modelToADMappingManager.loadMappingFromAccess(AccessDatabaseFileName);
    }
    System.exit(0);
  }

  private void loggerReport() {
    log.debug("Debug logging Enabled");
    log.info("Info Logging Enabled");
    log.warn("Warning Logging Enabled");
    log.error("Error Logging Enabled");
  }


}
