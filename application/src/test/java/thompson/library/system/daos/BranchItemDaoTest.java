package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import thompson.library.system.entities.Branch;
import thompson.library.system.entities.BranchItem;
import thompson.library.system.utilities.ConnectionManager;

import java.util.Calendar;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class BranchItemDaoTest {

    private SessionFactory sessionFactory;
    private java.sql.Timestamp joinDate;

    @Before
    public void setSessionFactory(){
        this.sessionFactory = ConnectionManager.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
    }

    @Test
    public void testUpdateBranchItem(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int bid = insertDate(session);
        BranchItemDaoImpl impl = new BranchItemDaoImpl(sessionFactory);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput =
                new BranchItemCheckoutDao.ItemReturnOutput(1, bid, true);

        impl.updateBranchItem(itemReturnOutput);

        BranchItem bItem = session.get(BranchItem.class, bid);
        assertTrue(bItem.isReserved());
        assertFalse(bItem.isCheckedout());

        session.getTransaction().rollback();
        session.close();

    }

    private int insertDate(Session session){
        BranchItem branchItem = new BranchItem();
        branchItem.setCheckedout(true);
        branchItem.setReserved(false);
        branchItem.setIntransit(false);

        Branch branch = new Branch();
        branch.setBranchid(1);
        branchItem.setBranchid(branch);
        branchItem.setCurrentlocation(branch);
        session.saveOrUpdate(branchItem);

        return branchItem.getBranchitemid();

    }

}
