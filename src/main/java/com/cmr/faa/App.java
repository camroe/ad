package com.cmr.faa;


import com.cmr.faa.jobmanager.ADManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    final static Logger log = LoggerFactory.getLogger(App.class);


    @Autowired
    private IntroMessage introMessage;

    @Autowired
    private ADManager adManager;

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
        if (!appArgs.isValid())
            System.out.println(appArgs.usageMessage());
        if (introMessage.isDisplaySpringBeans())
            introMessage.printBeans();
        loggerReport();
        //Real work here
        adManager.load();//Main entry point to the load process.
    }

    private void loggerReport() {
        log.debug("Debug logging Enabled");
        log.info("Info Logging Enabled");
        log.warn("Warning Logging Enabled");
        log.error("Error Logging Enabled");
    }


}
