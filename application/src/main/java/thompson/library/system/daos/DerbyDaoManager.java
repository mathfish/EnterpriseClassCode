package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

public class DerbyDaoManager implements DaoManager{

    private DerbyPatronDao patronDao;
    private DerbyReservationDao reservationDao;
    private DerbyBranchItemDao branchItemDao;
    private DerbyBranchItemCheckoutDao branchItemCheckoutDao;
    private DerbyCheckoutDao checkoutDao;

    DerbyDaoManager(){}

    public PatronDao getPatronDao(){
        if(patronDao == null){
            patronDao = new DerbyPatronDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return patronDao;
    }

    @Override
    public ReservationDao getReservationDao() {
        if(reservationDao == null){
            reservationDao = new DerbyReservationDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return reservationDao;
    }

    @Override
    public BranchItemDao getBranchItemDao() {
        if(branchItemDao == null){
            branchItemDao = new DerbyBranchItemDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return branchItemDao;
    }

    @Override
    public BranchItemCheckoutDao getBranchItemCheckoutDao() {
        if(branchItemCheckoutDao == null){
            branchItemCheckoutDao = new DerbyBranchItemCheckoutDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return branchItemCheckoutDao;
    }

    @Override
    public CheckoutDao getCheckoutDao() {
        if(checkoutDao == null){
            checkoutDao = new DerbyCheckoutDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return null;
    }

}
