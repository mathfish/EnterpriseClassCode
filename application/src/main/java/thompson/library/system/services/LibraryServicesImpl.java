package thompson.library.system.services;

import thompson.library.system.daos.DerbyDaoManager;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.PatronDTO;
import thompson.library.system.utilities.NonUniqueResultException;
import thompson.library.system.utilities.EntryExistsException;

import java.sql.Timestamp;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public class LibraryServicesImpl implements LibraryServices{

    @Override
    public boolean createPatron(String firstname,
                                String lastname,
                                String city,
                                String state,
                                int zipcode,
                                String streetAddress,
                                Timestamp joinDate,
                                String email,
                                long phone,
                                boolean remotelibrary,
                                String password) throws EntryExistsException {

        PatronDao patronDao = DerbyDaoManager.getPatronDao();
        try {
            if(patronDao.getPatron(email) != null){
                throw new EntryExistsException("Patron already exists with email " + email);
            }
        } catch (NonUniqueResultException e) {
            e.printStackTrace();
        }

        patronDao.insertPatron(new PatronDTO(firstname, lastname, city, state, zipcode, streetAddress, joinDate, email,
                                             phone, remotelibrary, password));

        return true;
    }
}
