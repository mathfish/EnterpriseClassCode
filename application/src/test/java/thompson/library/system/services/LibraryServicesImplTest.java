package thompson.library.system.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.utilities.EntryExistsException;
import thompson.library.system.utilities.LibraryConfig;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class LibraryServicesImplTest {

    @Autowired
    LibraryServices service;

    @Autowired
    JdbcOperations jdbcOperations;

    @Test(expected = EntryExistsException.class)
    public void testEmailExists() throws EntryExistsException {
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        String insert = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        jdbcOperations.update(insert,
                "ftest",
                "ltest",
                "ctest",
                "st",
                11111,
                "atest",
                joinDate,
                1111111111L,
                "ptest",
                false,
                "etest");

        service.createPatron("testFirst","testLast","testcity","AA",22222,"testAddress",null, "etest",
                        4444444444L, false, "1234PW");

    }

    @Test
    public void testCreatePatron(){
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        try {
            assertTrue(service.createPatron("testFirst","testLast","testcity","AA",22222,"testAddress",null, "etest",
                    4444444444L, false, "1234PW"));
        } catch (EntryExistsException e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }
}
