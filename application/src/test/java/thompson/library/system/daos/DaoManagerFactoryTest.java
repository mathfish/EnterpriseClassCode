package thompson.library.system.daos;

import org.junit.Test;
import static junit.framework.TestCase.*;

public class DaoManagerFactoryTest {

    @Test // Test appropriate manager is created and that it is a singleton
    public void getDaoManagerTest(){
        DaoManager daoManager = DaoManagerFactory.getDaoManager();
        assertNotNull(daoManager);
        DaoManager daoManager2 = DaoManagerFactory.getDaoManager();
        assertTrue("DaoManager is not a singleton",daoManager == daoManager2);
    }
}
