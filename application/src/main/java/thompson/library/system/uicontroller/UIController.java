package thompson.library.system.uicontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.services.BranchServices;


@Controller
public class UIController {
    private static final Logger log = LoggerFactory.getLogger(UIController.class);

    @Autowired
    BranchServices branchServices;

    @RequestMapping("/CreatePatron")
    public String createPatronRequest(@RequestParam(value="firstname", defaultValue = "testfirst") String firstname,
                                      @RequestParam(value = "lastname", defaultValue = "testlast") String lastname,
                                      @RequestParam(value = "city", defaultValue = "testcity") String city,
                                      @RequestParam(value = "state", defaultValue = "AA") String state,
                                      @RequestParam(value = "zip", defaultValue = "11111") String zip,
                                      @RequestParam(value = "address", defaultValue = "testaddress") String address,
                                      @RequestParam(value="email", defaultValue="testemail@rest") String email,
                                      @RequestParam(value = "phone", defaultValue = "1111111111") String phone,
                                      @RequestParam(value = "password", defaultValue = "pword") String password,
                                      Model model){

        RestTemplate restTemplate = new RestTemplate();
        String args = "firstname="+firstname+"&lastname="+lastname+"&city="+city+"&state="+state+"&zip="+zip+
                "&address="+address+"&email="+email+"&phone="+phone+"&password="+password;
        Response response = restTemplate.getForObject("http://localhost:8080/application/patron?"+args, Response.class);
        String message = "Response Code["+response.getId()+"]: "+response.getContent();
        model.addAttribute("message", message);
        return "patron";
    }

    @RequestMapping("/ItemReturn")
    public String returnItem(@RequestParam(value="branchItemId", defaultValue = "2") int branchItemId, Model model){
        BranchItemDto dto = new BranchItemDto(branchItemId, true, false, false, 1);
        try {
            branchServices.returnItem(dto);
            model.addAttribute("message","Item Returned Successfully");
        } catch(Exception ex){
            model.addAttribute("message","Item Not Return Successfully");
            log.info("Not returned due to {}", ex);
        }
        return "item";
    }
}
