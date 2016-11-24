package thompson.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("patron.wsdl");
        return marshaller;
    }

    @Bean
    public PatronClient patronClient(Jaxb2Marshaller marshaller) {
        PatronClient client = new PatronClient();
        client.setDefaultUri("http://localhost:8080/application/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
