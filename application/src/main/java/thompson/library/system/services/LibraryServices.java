package thompson.library.system.services;


import thompson.library.system.utilities.EntryExistsException;

import java.sql.Timestamp;

public interface LibraryServices {

    Boolean createPatron(String firstname,
                         String lastname,
                         String city,
                         String state,
                         int zipcode,
                         String streetAddress,
                         Timestamp joinDate,
                         String email,
                         long phone,
                         boolean remotelibrary,
                         String password)
            throws EntryExistsException;
}
