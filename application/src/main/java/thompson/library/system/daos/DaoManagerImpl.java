package thompson.library.system.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoManagerImpl implements DaoManager {


    private PatronDao patronDao;
    private ReservationDao reservationDao;
    private BranchItemDao branchItemDao;
    private BranchItemCheckoutDao branchItemCheckoutDao;
    private CheckoutDao checkoutDao;

    DaoManagerImpl(){}

    /**
     *
     * Returns the patronDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    public PatronDao getPatronDao(){
        return patronDao;
    }

    /**
     *
     * Returns the reservationDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    /**
     *
     * Returns the branchItemDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public BranchItemDao getBranchItemDao() {
        return branchItemDao;
    }

    /**
     *
     * Returns the branchItemCheckoutDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public BranchItemCheckoutDao getBranchItemCheckoutDao() {
        return branchItemCheckoutDao;
    }

    /**
     *
     * Returns the checkoutDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public CheckoutDao getCheckoutDao() {
        return checkoutDao;
    }

    @Autowired
    private void setPatronDao(PatronDao patronDao) {
        this.patronDao = patronDao;
    }

    @Autowired
    private void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Autowired
    private void setBranchItemDao(BranchItemDao branchItemDao) {
        this.branchItemDao = branchItemDao;
    }

    @Autowired
    private void setBranchItemCheckoutDao(BranchItemCheckoutDao branchItemCheckoutDao) {
        this.branchItemCheckoutDao = branchItemCheckoutDao;
    }

    @Autowired
    private void setCheckoutDao(CheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }
}
