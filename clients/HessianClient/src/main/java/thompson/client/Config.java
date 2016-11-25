package thompson.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@Configuration
public class Config {

    @Bean(name = "patronclient")
    public HessianProxyFactoryBean patronClient() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl("http://localhost:8080/application/PatronService");
        factory.setServiceInterface(PatronService.class);
        return factory;
    }
}
