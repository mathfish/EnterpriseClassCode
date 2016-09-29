package thompson.library.system.daos;

import org.junit.Test;

import static junit.framework.TestCase.*;
public class DerbyDaoManagerTest {

    @Test // Tests dao is created and that it is a singleton
    public void testGetPatronDao(){
        DerbyDaoManager impl = new DerbyDaoManager();
        PatronDao dao = impl.getPatronDao();
        assertNotNull("PatronDao is null",dao);
        PatronDao dao2 = impl.getPatronDao();
        assertTrue("PatronDao not singleton",dao == dao2);
    }
}
