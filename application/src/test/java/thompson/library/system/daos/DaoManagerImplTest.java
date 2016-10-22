package thompson.library.system.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thompson.library.system.utilities.LibraryConfig;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
public class DaoManagerImplTest {

    @Autowired
    DaoManager impl;

    @Test
    public void testGetPatronDao(){
        PatronDao p1 = impl.getPatronDao();
        assertNotNull(p1);
        assertTrue(p1 == impl.getPatronDao());
    }

    @Test
    public void testGetBranchItemCheckoutDao(){
        BranchItemCheckoutDao p1 = impl.getBranchItemCheckoutDao();
        assertNotNull(p1);
        assertTrue(p1 == impl.getBranchItemCheckoutDao());
    }

    @Test
    public void testGetBranchItemDao(){
        BranchItemDao p1 = impl.getBranchItemDao();
        assertNotNull(p1);
        assertTrue(p1 == impl.getBranchItemDao());
    }

    @Test
    public void testGetCheckoutDao(){
        CheckoutDao p1 = impl.getCheckoutDao();
        assertNotNull(p1);
        assertTrue(p1 == impl.getCheckoutDao());
    }

    @Test
    public void testGetReservationDao(){
        ReservationDao p1 = impl.getReservationDao();
        assertNotNull(p1);
        assertTrue(p1 == impl.getReservationDao());
    }

}
