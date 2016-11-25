package thompson.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        PatronService service = (PatronService)ctx.getBean("patronclient");
        String email = "testemail@hessian";
        if(args.length > 1){
            //first argument is server port for hessian client
            email = args[0];
        }
        String response = service.createPatron("testfirst", "testlast", "testcity", "AA", 11111, "test address", email,
                3333333333L, "password");
        log.info(response);
    }
}
