package thompson.library.system.daos;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static junit.framework.TestCase.*;

public class DaoManagerFactoryTest {

    @Test // Test appropriate manager is created and that it is a singleton
    public void getDaoManagerTest(){
        DaoManager daoManager = DaoManagerFactory.getDaoManager();
        File propFile = new File("database.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(properties.getProperty("databaseType").equals("derby")){
            assertTrue("manager not instance of DerbyDaoManager",daoManager instanceof DerbyDaoManager);
        } else {
            assertFalse(true);
        }


        DaoManager daoManager2 = DaoManagerFactory.getDaoManager();
        assertTrue("DaoManager is not a singleton",daoManager == daoManager2);
    }
}
