package thompson.library.system.daos;

import thompson.library.system.dtos.PatronD;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    PatronD getPatron(String email);

    PatronD getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    boolean insertPatron(PatronD patron);
}
