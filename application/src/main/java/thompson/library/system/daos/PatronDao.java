package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.NonUniqueResultException;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    PatronDto getPatron(String email) throws NonUniqueResultException;

    boolean insertPatron(PatronDto patron);
}
