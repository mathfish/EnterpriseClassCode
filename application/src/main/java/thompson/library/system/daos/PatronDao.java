package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    PatronDto getPatron(String email);

    PatronDto getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    boolean insertPatron(PatronDto patron);
}
