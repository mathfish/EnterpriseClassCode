package thompson.library.system.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.caucho.HessianServiceExporter;
import thompson.library.system.daos.BranchItemCheckoutDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.remote.hessian.PatronService;
import thompson.library.system.services.BranchServices;
import thompson.library.system.uicontroller.UIController;
import thompson.library.system.utilities.LoggingInterceptor;

@SpringBootApplication
@ComponentScan(basePackageClasses = {BranchItemCheckoutDao.class, BranchServices.class, LoggingInterceptor.class,
        PatronDto.class, Application.class, UIController.class})
public class Application extends SpringBootServletInitializer {

    @Autowired
    PatronService patronService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "/PatronService")
    public HessianServiceExporter accountService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(patronService);
        exporter.setServiceInterface(PatronService.class);
        return exporter;
    }
}
