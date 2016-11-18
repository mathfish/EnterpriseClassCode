package thompson.library.system.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.remote.Application;
import thompson.library.system.utilities.LibraryConfig;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class CheckoutDaoImplTest {

    @Autowired
    CheckoutDao checkoutDao;

    @Autowired
    JdbcOperations jdbcOperations;

    private java.sql.Timestamp date;

    private CheckoutDto insertCheckout(){
        Calendar calendar = Calendar.getInstance();
        date = new java.sql.Timestamp(calendar.getTime().getTime());
        String insert = "INSERT INTO checkout(patronid, checkoutdate, numberofitems, overdue, itemsreturned)" +
                " VALUES(1, ?, ?, false, false)";
        String query = "SELECT * FROM checkout WHERE patronid = 1 AND checkoutdate = ? AND numberofitems = ? " +
                "AND overdue = false AND itemsreturned = false";

        jdbcOperations.update(insert, date, 1);

        return jdbcOperations.queryForObject(query, (rs, rowNum) ->{
            return new CheckoutDto(rs.getInt("checkoutid"), rs.getInt("patronid"), rs.getTimestamp("checkoutdate"),
                    rs.getInt("numberofitems"), rs.getBoolean("overdue"), rs.getBoolean("itemsreturned"));
        }, date, 1);
    }


    @Test
    public void updateCheckoutTest(){
        CheckoutDto dto = insertCheckout();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.isReturned()).thenReturn(true);
        when(itemReturnOutput.getCheckoutid()).thenReturn(dto.getCheckoutID());
        checkoutDao.updateCheckout(itemReturnOutput);

        dto = jdbcOperations.queryForObject("SELECT * FROM checkout WHERE checkoutid = ?", (rs, rowNum) ->{
            return new CheckoutDto(rs.getInt("checkoutid"), rs.getInt("patronid"), rs.getTimestamp("checkoutdate"),
                    rs.getInt("numberofitems"), rs.getBoolean("overdue"), rs.getBoolean("itemsreturned"));
        }, dto.getCheckoutID());

        assertTrue(dto.isItemsreturned());
    }

    @Test
    public void getCheckoutTest(){
        CheckoutDto dto = insertCheckout();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getCheckoutid()).thenReturn(dto.getCheckoutID());

        CheckoutDto returnDto = checkoutDao.getCheckout(itemReturnOutput);
        assertEquals(date.toString(), returnDto.getCheckoutdate().toString());
        assertEquals(dto.getPatronid(), returnDto.getPatronid());
        assertEquals(dto.getNumberofitems(), returnDto.getNumberofitems());
        assertFalse(returnDto.isItemsreturned());
        assertFalse(returnDto.isOverdue());
    }
}
