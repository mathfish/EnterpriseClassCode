package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDTO;
import thompson.library.system.utilities.NonUniqueResultException;

import java.util.List;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public interface PatronDao {

    PatronDTO getPatron(int id);

    PatronDTO getPatron(String email) throws NonUniqueResultException;

    List<PatronDTO> getAllPatrons();

    boolean insertPatron(PatronDTO patron);

}
