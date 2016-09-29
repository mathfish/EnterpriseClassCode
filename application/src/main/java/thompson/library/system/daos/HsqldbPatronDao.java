package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.NonUniqueResultException;


public class HsqldbPatronDao implements PatronDao {

    HsqldbPatronDao(){}

    @Override
    public PatronDto getPatron(String email) throws NonUniqueResultException {
        return null;
    }

    @Override
    public boolean insertPatron(PatronDto patron) {
        return false;
    }
}
