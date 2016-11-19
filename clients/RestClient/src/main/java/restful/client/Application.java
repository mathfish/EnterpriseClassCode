package restful.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        Response response;
        RestTemplate restTemplate = new RestTemplate();
        if(args.length==1){
            response = restTemplate.getForObject("http://localhost:8080/application/patron"+args[0], Response.class);
        } else{
            response = restTemplate.getForObject("http://localhost:8080/application/patron", Response.class);
        }

        log.info(response.toString());
    }

}
