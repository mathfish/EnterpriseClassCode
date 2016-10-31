package thompson.library.system.daos;

import thompson.library.system.dtos.Dto;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    Dto getPatron(String email);

    Dto getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    boolean insertPatron(Dto patron);
}
