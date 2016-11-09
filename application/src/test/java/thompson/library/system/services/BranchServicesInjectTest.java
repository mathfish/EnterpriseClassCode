package thompson.library.system.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thompson.library.system.daos.*;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.LibraryConfig;

import java.lang.reflect.Field;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
public class BranchServicesInjectTest {

    @Autowired
    BranchServices branchServicesProxy;

    @Autowired
    JdbcOperations jdbcOperations;

    @Test
    public void branchDITest(){
        assertNotNull(branchServicesProxy);
        try{
            //Test DaoManager is injected into BranchServicesImpl using reflection
            Advised advised = (Advised)branchServicesProxy;
            BranchServices branchServices = (BranchServices)advised.getTargetSource().getTarget();

            Field daoManagerField = BranchServicesImpl.class.getDeclaredField("daoManager");
            daoManagerField.setAccessible(true);
            DaoManager daoManager = (DaoManager)daoManagerField.get(branchServices);
            assertNotNull(daoManager);

            // Verify Daos used in BranchServes are injected into DaoManager
            BranchItemCheckoutDao branchItemCheckoutDao = daoManager.getBranchItemCheckoutDao();
            assertNotNull(branchItemCheckoutDao);

            CheckoutDao checkoutDao = daoManager.getCheckoutDao();
            assertNotNull(checkoutDao);

            ReservationDao reservationDao = daoManager.getReservationDao();
            assertNotNull(reservationDao);

            BranchItemDao branchItemDao = daoManager.getBranchItemDao();
            assertNotNull(branchItemDao);

            PatronDao patronDao = daoManager.getPatronDao();
            assertNotNull(patronDao);

            //Verify jdbcOperations object not null and active
            assertNotNull(jdbcOperations);

            List<PatronDto> dtos =
                    jdbcOperations.query("SELECT * FROM patron", (rs, rowNum) -> {
                        return  new PatronDto(rs.getString("firstname"), rs.getString("lastname"), rs.getString("city"),
                                rs.getString("state"), rs.getInt("zipcode"), rs.getString("streetaddress"), rs.getTimestamp("joindate"),
                                rs.getString("email"), rs.getLong("phone"), rs.getBoolean("remotelibrary"), rs.getString("password"));
                    });

            assertFalse(dtos.isEmpty());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            assertFalse(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
