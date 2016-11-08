package thompson.library.system.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.utilities.*;

import java.sql.*;
import java.util.Calendar;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class BranchItemCheckoutDaoImplTest {

    @Autowired
    BranchItemCheckoutDao branchItemCheckoutDao;

    @Autowired
    JdbcOperations jdbcOperations;

    private java.sql.Date date;

    @Test
    public void testGetBranchItemCheckout(){
        insertBranchItemCheckoutRow(15, 3, false);
        BranchItemDto branchItemDto = mock(BranchItemDto.class);
        when(branchItemDto.getBranchitemid()).thenReturn(15);
        BranchItemCheckoutDto dto = branchItemCheckoutDao.getBranchItemCheckout(branchItemDto);

        assertEquals(15,dto.getBranchItemID().intValue());
        assertEquals(3,dto.getCheckoutID().intValue());
        assertFalse(dto.isOverdue());
        assertFalse(dto.isRenew());
        assertFalse(dto.isReturned());
        assertEquals(date.toString(), dto.getDueDate().toString());
    }

    private void insertBranchItemCheckoutRow(int branchitemid, int checkoutid, boolean returned) {
        Calendar calendar = Calendar.getInstance();
        date = new Date(calendar.getTime().getTime());
        String insert = "INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, renew, " +
                    "returned) VALUES (?,?,?,?,?,?)";
        jdbcOperations.update(insert, branchitemid, checkoutid, false, date, false, returned);
    }


    @Test
    public void testUpdateBranchItemCheckout(){
        insertBranchItemCheckoutRow(15, 3, false);

        BranchItemCheckoutDto branchItemCheckoutDto = mock(BranchItemCheckoutDto.class);
        when(branchItemCheckoutDto.getBranchItemID()).thenReturn(15);
        when(branchItemCheckoutDto.getCheckoutID()).thenReturn(3);
        when(branchItemCheckoutDto.getReturnDate()).thenReturn(date);
        when(branchItemCheckoutDto.isOverdue()).thenReturn(true);
        when(branchItemCheckoutDto.isReturned()).thenReturn(true);

        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = branchItemCheckoutDao.updateBranchItemCheckout(branchItemCheckoutDto);

        assertEquals(15,itemReturnOutput.getBranchitemid().intValue());
        assertEquals(3, itemReturnOutput.getCheckoutid().intValue());

        BranchItemCheckoutDto dto =
        jdbcOperations.queryForObject("SELECT * FROM branchitemcheckout WHERE branchitemid = 15 AND checkoutid = 3",
                (rs, rowNum) -> {
                   return new BranchItemCheckoutDto(rs.getInt("branchitemid"),
                           rs.getInt("checkoutid"), rs.getBoolean("overdue"), rs.getDate("duedate"),rs.getBoolean("renew"),
                           rs.getDate("renewdate"), rs.getBoolean("returned"), rs.getDate("returndate"));
                });

        assertEquals(date.toString(), dto.getReturnDate().toString());
        assertTrue(dto.isReturned());
        assertTrue(dto.isReturned());
    }

    @Test
    public void testGetNumberOfItemsReturnedFromCheckout(){
        insertBranchItemCheckoutRow(15, 1, true);
        insertBranchItemCheckoutRow(14, 1, true);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getCheckoutid()).thenReturn(1);
        int count = branchItemCheckoutDao.getNumberOfItemsReturnedFromCheckout(itemReturnOutput);
        assertEquals(2,count);
    }
}
