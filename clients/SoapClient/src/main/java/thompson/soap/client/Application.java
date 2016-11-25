package thompson.soap.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import patron.wsdl.GetPatronCreateResponse;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static String[] arguments;
    @Autowired
    PatronClient client;


    public static void main(String[] args) {
        arguments = args;
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String[] args) throws Exception {
        String email = "testemail@soap";
        if (arguments.length > 0) {
            email = arguments[0];
        }
        GetPatronCreateResponse response = client.getPatronCreateResponse(email);
        client.logResponse(response);
    }
}
