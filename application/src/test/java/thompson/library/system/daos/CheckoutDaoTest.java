package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.entities.Checkout;
import thompson.library.system.entities.Patron;
import thompson.library.system.utilities.ConnectionManager;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckoutDaoTest {

    private SessionFactory sessionFactory;
    private java.sql.Timestamp joinDate;

    @Before
    public void setSessionFactory(){
        this.sessionFactory = ConnectionManager.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
    }


    @Test
    public void testUpdateCheckout(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CheckoutDaoImpl impl = new CheckoutDaoImpl(sessionFactory);
        int cid = insertData(session);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput =
                new BranchItemCheckoutDao.ItemReturnOutput(cid,1,true);
        itemReturnOutput.setReturned(true);
        impl.updateCheckout(itemReturnOutput);

        Checkout ans =
                (Checkout)session.createQuery("SELECT ch FROM Checkout ch WHERE ch.checkoutid = :id")
                        .setParameter("id", cid)
                        .getSingleResult();

        assertTrue(ans.isItemsreturned());

        session.getTransaction().rollback();
        session.close();
    }


    @Test
    public void testGetCheckout(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CheckoutDaoImpl impl = new CheckoutDaoImpl(sessionFactory);
        int cid = insertData(session);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput =
                new BranchItemCheckoutDao.ItemReturnOutput(cid,1,true);
        CheckoutDto dto = impl.getCheckout(itemReturnOutput);
        assertEquals(joinDate, dto.getCheckoutdate());
        assertFalse(dto.isOverdue());
        assertEquals(4, dto.getNumberofitems());
        assertFalse(dto.isItemsreturned());

        session.getTransaction().rollback();
        session.close();
    }



    private int insertData(Session session){
        Checkout checkout = new Checkout();
        checkout.setOverdue(false);
        checkout.setItemsreturned(false);
        checkout.setNumberofitems(4);
        checkout.setCheckoutdate(joinDate);
        Patron patron = new Patron();
        patron.setPatronid(1);
        checkout.setPatronid(patron);
        session.saveOrUpdate(checkout);

        return checkout.getCheckoutid();
    }

}
