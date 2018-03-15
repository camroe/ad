package com.cmr.faa.jobmanager;

import com.healthmarketscience.jackcess.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Manager {
    final static Logger log = LoggerFactory.getLogger(Manager.class);


    public void describeDatabase(String accessDatabaseFileName) {
        log.trace("About to describe database as found in " + accessDatabaseFileName);
        Database db = null;
        if (checkExistence(accessDatabaseFileName)) {
            try {
                db = DatabaseBuilder.open(new File(ADManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile()));
                for (String tableName :
                        db.getTableNames()) {
                    Table table = db.getTable(tableName);
                    System.out.println("TABLE NAME:" + tableName + ":" + table.getRowCount());
                    Row row = table.getNextRow();
                    for (Column colum :
                            table.getColumns()) {
                        String columnName = colum.getName();
                        Object value = row.get(columnName);
                        if (null != value) {
                            System.out.println("\t" + "Column " + columnName + "("
                                    + colum.getType() + "):"
                                    + value + " ("
                                    + value.getClass() + ")");
                        } else {
                            System.out.println("\t" + "Column " + columnName + "("
                                    + colum.getType() + "):"
                                    + value + " ("
                                    + "Unknown Type" + ")");
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean checkExistence(String accessDatabaseFileName) {
        boolean exists = false;

        ClassLoader classLoader = ADManager.class.getClassLoader();
        URL url = classLoader.getResource(accessDatabaseFileName);
        if (null != url) {
            File accessDatabaseFile = new File(ADManager.class.getClassLoader().getResource(accessDatabaseFileName).getFile());
            if (accessDatabaseFile.exists()) {
                log.trace("Access file exists " + "'" + accessDatabaseFile.getAbsolutePath() + "'");
                exists = true;
            } else {
                log.trace("Access file Does NOT exist " + "'" + accessDatabaseFileName + "'");
                try {
                    log.trace(accessDatabaseFile.getCanonicalPath());
                    File temp = File.createTempFile("Testfile", ".tmp");
                    log.trace(temp.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            log.trace("Access file Does NOT exist " + "'" + accessDatabaseFileName + "'");
        }
        return exists;
    }


}
