package thompson.library.system.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DaoManagerFactory {

    private static DerbyDaoManager derbyDaoManager;

    private DaoManagerFactory(){}

    public static DaoManager getDaoManager(){
        File file = new File("database.properties");
        Properties properties = null;
        try(FileInputStream fios = new FileInputStream(file)){
            properties = new Properties();
            properties.load(fios);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(properties.getProperty("databaseType").equals("derby")){
            if(derbyDaoManager == null){
                derbyDaoManager = new DerbyDaoManager();
            }
            return derbyDaoManager;
        }

        return  null;
    }

}
