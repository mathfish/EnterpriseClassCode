package thompson.library.system.remote.hessian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thompson.library.system.services.LibraryServices;
import thompson.library.system.utilities.EntryExistsException;

import java.util.Calendar;

@Service("patronService")
public class PatronServiceImpl implements PatronService{

    @Autowired
    LibraryServices libraryServices;

    @Override
    public String createPatron(String firstname, String lastname, String city, String state,
                                        int zip, String address, String email, long phone, String password) {
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        try {
            libraryServices.createPatron(firstname, lastname, city, state, zip, address, joinDate, email, phone, false,
                    password);
            return "Response Code[1]: Patron successfully created!";
        } catch (EntryExistsException e) {
            return "Response Code[0]: Patron already exists with email " + email;
        }
    }
}
