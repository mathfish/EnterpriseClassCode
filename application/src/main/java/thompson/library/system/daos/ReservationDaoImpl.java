package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.entities.Reservation;

import java.util.List;

public class ReservationDaoImpl implements ReservationDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
    private SessionFactory sessionFactory;

    ReservationDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Used to mark a reservation as being fulfilled, if necessary, as part of many steps of the return process
     */
    @Override
    public ReservationDto fulfillReservation(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        List<Reservation> reservations =
        currentSession.createQuery("SELECT res FROM Reservation res WHERE res.branchItemid.branchitemid = :bid and " +
                "res.fulfilled = :fulfilled ORDER BY res.reservdate")
                .setParameter("bid", itemReturnOutput.getBranchitemid())
                .setParameter("fulfilled", false)
                .getResultList();

        ReservationDto reservationDto = null;
        if(reservations.size() > 0) {
            Reservation reservation = reservations.get(0);
            reservation.setFulfilled(true);
            currentSession.saveOrUpdate(reservation);
            reservationDto = new ReservationDto(reservation.getReservationid(), reservation.getPatronid().getPatronid(),
                    reservation.getBranchItemid().getBranchitemid(), reservation.getReservdate(), true,
                    reservation.getForbranchid().getBranchid());
        }
        if(commitTrans){
            currentSession.getTransaction().commit();
        }

        return reservationDto;
    }

}
