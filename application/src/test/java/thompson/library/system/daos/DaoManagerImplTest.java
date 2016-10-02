package thompson.library.system.daos;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DaoManagerImplTest {

    DaoManagerImpl impl = new DaoManagerImpl();

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
