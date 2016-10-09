package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.entities.BranchItem;
import thompson.library.system.entities.BranchItemCheckout;
import thompson.library.system.entities.Checkout;
import thompson.library.system.utilities.ConnectionManager;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class BranchItemCheckoutDaoTest {
    private SessionFactory sessionFactory;
    private java.sql.Date joinDate;

    @Before
    public void setSessionFactory(){
        this.sessionFactory = ConnectionManager.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        joinDate = new java.sql.Date(calendar.getTime().getTime());
    }

    @Test
    public void testGetBranchItemCheckout(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        BranchItemCheckoutDaoImpl impl = new BranchItemCheckoutDaoImpl(sessionFactory);
        insertData(session,1, false);

        BranchItemDto dtoIn = new BranchItemDto(1, false, false, false, 1);
        BranchItemCheckoutDto dto = impl.getBranchItemCheckout(dtoIn);
        assertEquals(joinDate.toString(), dto.getDueDate().toString());
        assertEquals(joinDate.toString(), dto.getRenewDate().toString());
        assertEquals(joinDate.toString(), dto.getReturnDate().toString());
        assertFalse(dto.isReturned());
        assertFalse(dto.isOverdue());
        assertFalse(dto.isRenew());
        assertEquals(1,dto.getCheckoutID().intValue());
        assertEquals(1,dto.getBranchItemID().intValue());
        session.getTransaction().rollback();
        session.close();
    }


    @Test
    public void testUpdateBranchItemCheckout(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        BranchItemCheckoutDaoImpl impl = new BranchItemCheckoutDaoImpl(sessionFactory);
        insertData(session,1, false);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date update = new java.sql.Date(calendar.getTime().getTime());
        BranchItemCheckoutDto dto = new BranchItemCheckoutDto(1, 1, true, update, true, update, true, update);
        impl.updateBranchItemCheckout(dto);

        BranchItemCheckout ans =
        (BranchItemCheckout)session.createQuery("SELECT bic from BranchItemCheckout bic where " +
                "bic.branchitemid.branchitemid = :bid and " +
                "bic.checkoutid.checkoutid = :cid")
                .setParameter("bid", 1)
                .setParameter("cid", 1)
                .getSingleResult();


        assertFalse(ans.isRenew());
        assertTrue(ans.isOverdue());
        assertTrue(ans.isReturned());
        assertEquals(joinDate.toString(), ans.getDuedate().toString());
        assertEquals(joinDate.toString(), ans.getRenewdate().toString());
        assertEquals(joinDate.toString(), ans.getReturndate().toString());

        session.getTransaction().rollback();
        session.close();
    }


    @Test
    public void testGetNumberOfItemsReturnedFromCheckout(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        BranchItemCheckoutDaoImpl impl = new BranchItemCheckoutDaoImpl(sessionFactory);
        insertData(session,1, true);
        insertData(session,12, true);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = new BranchItemCheckoutDao.ItemReturnOutput(1,1,false);
        int numItems = impl.getNumberOfItemsReturnedFromCheckout(itemReturnOutput);
        assertEquals(2,numItems);

        session.getTransaction().rollback();
        session.close();
    }


    private void insertData(Session session, int bid, boolean returned){
        BranchItemCheckout branchItemCheckout = new BranchItemCheckout();

        BranchItem bitem = new BranchItem();
        bitem.setBranchitemid(bid);
        Checkout checkout = new Checkout();
        checkout.setCheckoutid(1);

        branchItemCheckout.setReturned(returned);
        branchItemCheckout.setOverdue(false);
        branchItemCheckout.setRenew(false);
        branchItemCheckout.setRenewdate(joinDate);
        branchItemCheckout.setReturndate(joinDate);
        branchItemCheckout.setDuedate(joinDate);
        branchItemCheckout.setBranchitemid(bitem);
        branchItemCheckout.setCheckoutid(checkout);
        session.saveOrUpdate(branchItemCheckout);

    }


}
