package thompson.library.system.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.PatronDto;

import thompson.library.system.utilities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class PatronDaoImplTest {

    @Autowired
    PatronDao patronDao;

    @Autowired
    JdbcOperations jdbcOperations;

    @Test
    public void getPatronTestByEmail(){
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

        PatronDto dto  = patronDao.getPatron("etest");
        assertNotNull(dto);
        assertEquals("ftest", dto.getFirstname());
        assertEquals("ltest", dto.getLastname());
        assertEquals("ctest", dto.getCity());
        assertEquals("st", dto.getState());
        assertEquals(11111, dto.getZipcode());
        assertEquals("atest", dto.getStreetAddress());
        assertEquals("etest", dto.getEmail());
        assertEquals(1111111111L, dto.getPhone());
        assertEquals(false, dto.isRemotelibrary());
        assertEquals("ptest", dto.getPassword());
    }

    @Test
    public void getPatronByItemReturnOutput(){
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

        int id = jdbcOperations.queryForObject("SELECT patronid FROM patron WHERE email = ?",
                                                new Object[]{"etest"},
                                                Integer.class);
        BranchItemCheckoutDao.ItemReturnOutput out = new BranchItemCheckoutDao.ItemReturnOutput(null, null, null, false);
        out.setPatronid(id);
        PatronDto dto  = patronDao.getPatron(out);
        assertNotNull(dto);
        assertEquals("ftest", dto.getFirstname());
        assertEquals("ltest", dto.getLastname());
        assertEquals("ctest", dto.getCity());
        assertEquals("st", dto.getState());
        assertEquals(11111, dto.getZipcode());
        assertEquals("atest", dto.getStreetAddress());
        assertEquals("etest", dto.getEmail());
        assertEquals(1111111111L, dto.getPhone());
        assertEquals(false, dto.isRemotelibrary());
        assertEquals("ptest", dto.getPassword());
    }

    @Test
    public void insertPatronTest(){
        patronDao.insertPatron(getDto());
        String query = "SELECT * FROM patron WHERE email = ?";
        PatronDto dto = null;
        dto = jdbcOperations.queryForObject(query, ((rs, rowNum) -> {
            return new PatronDto(rs.getInt("patronid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getInt("zipcode"),
                    rs.getString("streetaddress"),
                    rs.getTimestamp("joindate"),
                    rs.getString("email"),
                    rs.getLong("phone"),
                    rs.getShort("remotelibrary") == 1,
                    rs.getString("password"));
        }), "test@email.test");

        assertEquals("testFirst", dto.getFirstname());
        assertEquals("testLast", dto.getLastname());
        assertEquals("testCity", dto.getCity());
        assertEquals("ST", dto.getState());
        assertEquals(99999, dto.getZipcode());
        assertEquals("testStreetAddress", dto.getStreetAddress());
        assertEquals("test@email.test", dto.getEmail());
        assertEquals(9999999999L, dto.getPhone());
        assertFalse(dto.isRemotelibrary());
        assertEquals("testPW", dto.getPassword());
    }

    private PatronDto getDto(){
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        return new PatronDto("testFirst", "testLast", "testCity", "ST", 99999,
                "testStreetAddress",joinDate, "test@email.test", 9999999999L,false, "testPW");
    }
}
