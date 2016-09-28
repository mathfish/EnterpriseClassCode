package thompson.library.system.daos;

import thompson.library.system.utilities.DerbyConnectionFactory;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public class DerbyDaoManager{

    private static PatronDao patronDao = null;



    public static PatronDao getPatronDao(){
        if(patronDao == null){
            patronDao = new PatronDaoDerby();
        }
        return patronDao;
    }


}
