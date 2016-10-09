package thompson.library.system.utilities;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     *
     * Uses the database.properties file to determine which connection to return. Must be a supported database
     */
    private static SessionFactory buildSessionFactory(){
        File file = new File("database.properties");
        Properties properties = null;
        SessionFactory sessionFactory = null;
        try(FileInputStream fileIS = new FileInputStream(file)){
            properties = new Properties();
            properties.load(fileIS);
        } catch (IOException e) {
            logger.error("Error loading properties file", e);
            throw new IllegalStateException("Unable to load properties file. See log for details");
        }
        if(properties.getProperty("databaseType").equals("derby")){
           sessionFactory =  new Configuration().configure("derby.cfg.xml").buildSessionFactory();
        } else if(properties.getProperty("databaseType").equals("hsql")){
            sessionFactory = new Configuration().configure("hsql.cfg.xml").buildSessionFactory();
        } else{
            logger.error("Unsupported database {} found in properties file", properties.getProperty("datbaseType"));
            throw new IllegalStateException("Unsupported database found in properties file. See log for details");
        }

        return sessionFactory;
    }



    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
