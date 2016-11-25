package thompson.library.system.remote.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thompson.library.system.services.LibraryServices;
import thompson.library.system.utilities.EntryExistsException;

import java.util.Calendar;

@RestController
public class RemoteController {

    @Autowired
    LibraryServices service;

    @RequestMapping("/patron")
    public Response greeting( @RequestParam(value="firstname", defaultValue = "testfirst") String firstname,
                              @RequestParam(value = "lastname", defaultValue = "testlast") String lastname,
                              @RequestParam(value = "city", defaultValue = "testcity") String city,
                              @RequestParam(value = "state", defaultValue = "AA") String state,
                              @RequestParam(value = "zip", defaultValue = "11111") String zip,
                              @RequestParam(value = "address", defaultValue = "testaddress") String address,
                              @RequestParam(value="email", defaultValue="testemail@rest") String email,
                              @RequestParam(value = "phone", defaultValue = "1111111111") String phone,
                              @RequestParam(value = "password", defaultValue = "pword") String password
                              ) {
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        try {
            service.createPatron(firstname,lastname,city,state,Integer.parseInt(zip),address, joinDate, email,
                    Long.parseLong(phone), false, password);
            return new Response(1,"Patron successfully created!");
        } catch (EntryExistsException e) {
            return new Response(0,"ERROR: Patron already exists with email " + email);
        }

    }

}
