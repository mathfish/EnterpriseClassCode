package thompson.library.system.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thompson.library.system.services.LibraryServices;
import thompson.library.system.utilities.EntryExistsException;

@RestController
public class RemoteController {
    private String message;
    private Integer id;

    @Autowired
    LibraryServices service;

    @RequestMapping("/patron")
    public Response greeting(@RequestParam(value="email", defaultValue="none") String email) {

        try {
            service.createPatron("testFirst","testLast","testcity","AA",22222,"testAddress",null, email,
                    4444444444L, false, "1234PW");
            return new Response(1,"yes");
        } catch (EntryExistsException e) {
            return new Response(0,"no");
        }

    }

}
