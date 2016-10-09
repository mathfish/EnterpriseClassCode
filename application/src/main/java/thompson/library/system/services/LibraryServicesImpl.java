package thompson.library.system.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.daos.DaoManager;
import thompson.library.system.daos.DaoManagerFactory;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.EntryExistsException;

import java.sql.Timestamp;

public class LibraryServicesImpl implements LibraryServices{

    private DaoManager daoManager;
    private static final Logger logger = LoggerFactory.getLogger(LibraryServicesImpl.class);

    LibraryServicesImpl(DaoManager daoManager){
        this.daoManager = daoManager;
    }

    /**
     *
     * Used to create a patron in the library system. First checks if the patron already exists by their email, as
     * email is unique.
     */
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

        PatronDao patronDao = daoManager.getPatronDao();

        if(patronDao.getPatron(email) != null){
            logger.info("Patron with email {} already exists", email);
            throw new EntryExistsException("Patron already exists with email " + email);
        }

        patronDao.insertPatron(new PatronDto(firstname, lastname, city, state, zipcode, streetAddress, joinDate, email,
                                             phone, remotelibrary, password));

        return true;
    }
}
