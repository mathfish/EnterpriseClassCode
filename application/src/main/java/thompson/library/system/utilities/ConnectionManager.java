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
    private static DerbyConnectionFactory derbyConnectionFactory;
    private static HsqlConnectionFactory hsqlConnectionFactory;

    /**
     *
     * Uses the database.properties file to determine which connection to return. Must be a supported database
     */
    public static ConnectionFactory getConnectionFactory(){
        File file = new File("database.properties");
        Properties properties = null;


        try(FileInputStream fileIS = new FileInputStream(file)){
            properties = new Properties();
            properties.load(fileIS);
        } catch (IOException e) {
            logger.error("Error loading properties file", e);
            throw new IllegalStateException("Unable to load properties file. See log for details");
        }

        if(properties.getProperty("databaseType").equals("derby")){
            if(derbyConnectionFactory == null){
                derbyConnectionFactory = new DerbyConnectionFactory();
            }
            return derbyConnectionFactory;
        } else if(properties.getProperty("databaseType").equals("hsql")){
            if(hsqlConnectionFactory == null){
                hsqlConnectionFactory = new HsqlConnectionFactory();
            }
            return hsqlConnectionFactory;
        } else{
            logger.error("Unsupported database in properties file");
            throw new IllegalStateException("Unsupported database type in properties file");
        }
    }

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
        }

        return sessionFactory;
    }



    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
