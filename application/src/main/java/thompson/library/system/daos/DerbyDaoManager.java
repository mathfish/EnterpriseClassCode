package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

public class DerbyDaoManager implements DaoManager{

    private DerbyPatronDao patronDao;
    private DerbyReservationDao reservationDao;
    private DerbyBranchItemDao branchItemDao;

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

}
