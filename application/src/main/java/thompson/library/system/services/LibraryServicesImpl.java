package thompson.library.system.services;

import thompson.library.system.daos.DaoManager;
import thompson.library.system.daos.DaoManagerFactory;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.NonUniqueResultException;
import thompson.library.system.utilities.EntryExistsException;

import java.sql.Timestamp;

public class LibraryServicesImpl implements LibraryServices{

    private DaoManager daoManager;

    public LibraryServicesImpl(){
        this.daoManager = DaoManagerFactory.getDaoManager();
    }

    LibraryServicesImpl(DaoManager daoManager){
        this.daoManager = daoManager;
    }

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
            throw new EntryExistsException("Patron already exists with email " + email);
        }

        patronDao.insertPatron(new PatronDto(firstname, lastname, city, state, zipcode, streetAddress, joinDate, email,
                                             phone, remotelibrary, password));

        return true;
    }
}
