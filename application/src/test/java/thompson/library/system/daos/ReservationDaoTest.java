package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.entities.Branch;
import thompson.library.system.entities.BranchItem;
import thompson.library.system.entities.Patron;
import thompson.library.system.entities.Reservation;
import thompson.library.system.utilities.ConnectionManager;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReservationDaoTest {

    private SessionFactory sessionFactory;
    private java.sql.Timestamp joinDate;

    @Before
    public void setSessionFactory(){
        this.sessionFactory = ConnectionManager.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
    }

    @Test
    public void testFulfillResrvation(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int rid = insertData(session);
        ReservationDaoImpl impl = new ReservationDaoImpl(sessionFactory);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = new BranchItemCheckoutDao.ItemReturnOutput(1,1,false);
        ReservationDto dto = impl.fulfillReservation(itemReturnOutput);

        assertEquals(joinDate.toString(),dto.getReservDate().toString());
        assertTrue(dto.isFulfilled());
        Reservation reservation =
        (Reservation)session.createQuery("SELECT res FROM Reservation res WHERE res.reservationid = :rid")
                .setParameter("rid", rid)
                .getSingleResult();
        assertTrue(reservation.isFulfilled());

        session.getTransaction().rollback();
        session.close();

    }



    private int  insertData(Session session){
        Reservation reservation = new Reservation();
        reservation.setFulfilled(false);
        reservation.setReservdate(joinDate);
        BranchItem branchItem = new BranchItem();
        branchItem.setBranchitemid(1);
        Patron patron = new Patron();
        patron.setPatronid(1);
        Branch branch = new Branch();
        branch.setBranchid(1);
        reservation.setBranchItemid(branchItem);
        reservation.setPatronid(patron);
        reservation.setForbranchid(branch);
        session.saveOrUpdate(reservation);
        return reservation.getReservationid();

    }


}
