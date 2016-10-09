package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionManager;

public class DaoManagerImpl implements DaoManager {


    private PatronDaoImpl patronDao;
    private ReservationDaoImpl reservationDao;
    private BranchItemDaoImpl branchItemDao;
    private BranchItemCheckoutDaoImpl branchItemCheckoutDao;
    private CheckoutDaoImpl checkoutDao;

    DaoManagerImpl(){}

    /**
     *
     * Returns the patronDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    public PatronDao getPatronDao(){
        if(patronDao == null){
            patronDao = new PatronDaoImpl(ConnectionManager.getSessionFactory());
        }
        return patronDao;
    }

    /**
     *
     * Returns the reservationDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public ReservationDao getReservationDao() {
        if(reservationDao == null){
            reservationDao = new ReservationDaoImpl(ConnectionManager.getSessionFactory());
        }
        return reservationDao;
    }

    /**
     *
     * Returns the branchItemDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public BranchItemDao getBranchItemDao() {
        if(branchItemDao == null){
            branchItemDao = new BranchItemDaoImpl(ConnectionManager.getSessionFactory());
        }
        return branchItemDao;
    }

    /**
     *
     * Returns the branchItemCheckoutDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public BranchItemCheckoutDao getBranchItemCheckoutDao() {
        if(branchItemCheckoutDao == null){
            branchItemCheckoutDao = new BranchItemCheckoutDaoImpl(ConnectionManager.getSessionFactory());
        }
        return branchItemCheckoutDao;
    }

    /**
     *
     * Returns the checkoutDao object, a singleton. Not intended to be threadsafe or clone safe.
     */
    @Override
    public CheckoutDao getCheckoutDao() {
        if(checkoutDao == null){
            checkoutDao = new CheckoutDaoImpl(ConnectionManager.getSessionFactory());
        }
        return checkoutDao;
    }
}
