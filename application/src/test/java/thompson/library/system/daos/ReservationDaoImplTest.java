package thompson.library.system.daos;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.utilities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class ReservationDaoImplTest {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    JdbcOperations jdbcOperations;

    private java.sql.Date date;

    @Test
    public void fulfillReservationTestAllFulfilled(){
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getBranchitemid()).thenReturn(15);
        assertNull(reservationDao.fulfillReservation(itemReturnOutput));
    }

    @Test
    public void fullfillReservationTestNotAllFulfilled(){
        String insert =" INSERT INTO reservation(patronid, forbranchid, branchitemid, reservdate, fulfilled) " +
                    "VALUES(1,1,15, ? , ?)";
        String query = "SELECT reservationid FROM reservation WHERE patronid = 1 AND forbranchid = 1 AND branchitemid = 15" +
                    " AND reservdate = ? AND fulfilled = false";
        Calendar calendar = Calendar.getInstance();
        date = new java.sql.Date(calendar.getTime().getTime());

        jdbcOperations.update(insert, date, false);
        int id = jdbcOperations.queryForObject(query, new Object[]{date}, Integer.class);

        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getBranchitemid()).thenReturn(15);
        ReservationDto dto = reservationDao.fulfillReservation(itemReturnOutput);
        assertEquals(id, dto.getReservationid());
        assertEquals(15, dto.getBranchitemid());
        assertTrue(dto.isFulfilled());

    }

}
