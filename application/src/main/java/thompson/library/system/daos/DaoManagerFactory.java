package thompson.library.system.daos;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoManagerFactory {
    private static DaoManagerImpl daoManagerimpl;
    private static final Logger logger = LoggerFactory.getLogger(DaoManagerFactory.class);

    private DaoManagerFactory(){}

    /**
     * Used to return a daoManger singleton. Not intended to be threadsafe or clone safe.
     */

    public static DaoManager getDaoManager(){
        if(daoManagerimpl == null){
            daoManagerimpl = new DaoManagerImpl();
        }

        return daoManagerimpl;
    }

}
