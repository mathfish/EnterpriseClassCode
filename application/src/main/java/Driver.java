import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thompson.library.system.daos.BranchItemCheckoutDao;
import thompson.library.system.daos.DaoDummy;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.LibraryConfig;

import java.util.List;

public class Driver {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LibraryConfig.class);
        DaoDummy daoDummy = context.getBean(DaoDummy.class);
        List<PatronDto> dtos = daoDummy.getAllPatronsByFirstName("somename");
        for(PatronDto dto : dtos){
            System.out.println(dto.getPatronid() + " " + dto.getFirstname() + " " + dto.getLastname() + " " + dto.getEmail());
        }

        if(dtos == null){
            System.out.println("null");
        }
        if(dtos.isEmpty()){
            System.out.println("empty");
        }
//        PatronDao patronDao = context.getBean(PatronDao.class);
//        PatronDto dto = patronDao.getPatron("jd@email2.com");
//        System.out.println(dto == null);
        //System.out.println(dto.getFirstname() + " " + dto.getLastname());
    }
}
