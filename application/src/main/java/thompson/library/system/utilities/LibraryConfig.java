package thompson.library.system.utilities;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import thompson.library.system.daos.BranchItemCheckoutDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.services.BranchServices;

@Configuration
@ComponentScan(basePackageClasses = {BranchItemCheckoutDao.class, BranchServices.class, ConnectionFactory.class,
        PatronDto.class})
@EnableAspectJAutoProxy
public class LibraryConfig {
}
