package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.NonUniqueResultException;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    PatronDto getPatron(String email);

    PatronDto getPatrion(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    boolean insertPatron(PatronDto patron);
}
