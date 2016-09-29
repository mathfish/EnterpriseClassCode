package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

public class DerbyDaoManager implements DaoManager{

    private DerbyPatronDao patronDao;

    DerbyDaoManager(){}

    public PatronDao getPatronDao(){
        if(patronDao == null){
            patronDao = new DerbyPatronDao(new DerbyConnectionFactory(), new ConnectionUtil());
        }
        return patronDao;
    }

}
