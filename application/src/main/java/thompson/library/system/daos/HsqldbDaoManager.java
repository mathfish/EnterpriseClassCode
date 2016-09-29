package thompson.library.system.daos;

public class HsqldbDaoManager implements DaoManager {

    private HsqldbPatronDao hsqldbPatronDao;

    HsqldbDaoManager(){}

    @Override
    public PatronDao getPatronDao() {
        if(hsqldbPatronDao == null){
            hsqldbPatronDao = new HsqldbPatronDao();
        }
        return hsqldbPatronDao;
    }
}
