package thompson.library.system.daos;


import org.junit.Test;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionManager;
import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

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

public class ReservationDaoImplTest {

    private Connection connection;
    private java.sql.Date date;
    private int reservationid =0;

    private Connection getLocalConnection(boolean isFulfilled){
        ConnectionFactory connectionFactory = ConnectionManager.getConnectionFactory();
        connection = connectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Calendar calendar = Calendar.getInstance();
        date = new java.sql.Date(calendar.getTime().getTime());

        try {
            connection.setAutoCommit(false);
            String insert =" INSERT INTO reservation(patronid, forbranchid, branchitemid, reservdate, fulfilled) " +
                    "VALUES(1,1,15, ? , ?)";
            String query = "SELECT reservationid FROM reservation WHERE patronid = 1 AND forbranchid = 1 AND branchitemid = 15" +
                    " AND reservdate = ? AND fulfilled = false";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setDate(1,date);
            preparedStatement.setBoolean(2, isFulfilled);
            preparedStatement.executeUpdate();
            if(!isFulfilled) {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, date);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    reservationid = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            TestConnectionUtil util = new TestConnectionUtil();
            util.close(preparedStatement);
            util.close(resultSet);
        }
        return connection;
    }

    @Test
    public void fulfillReservationTestAllFulfilled(){
        TestConnectionUtil util = new TestConnectionUtil();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getConnection()).thenReturn(getLocalConnection(true));
        when(itemReturnOutput.getBranchitemid()).thenReturn(15);
        ReservationDaoImpl impl = new ReservationDaoImpl(null, util);
        assertNull(impl.fulfillReservation(itemReturnOutput));
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            util.close(connection);
        }

    }

    @Test
    public void fullfillReservationTestNotAllFulfilled(){
        TestConnectionUtil util = new TestConnectionUtil();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getConnection()).thenReturn(getLocalConnection(false));
        when(itemReturnOutput.getBranchitemid()).thenReturn(15);
        ReservationDaoImpl impl = new ReservationDaoImpl(null, util);
        ReservationDto dto = impl.fulfillReservation(itemReturnOutput);
        assertEquals(reservationid, dto.getReservationid());
        assertEquals(15,dto.getBranchitemid());
        assertTrue(dto.isFulfilled());
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            util.close(connection);
        }
    }

    private class TestConnectionUtil extends ConnectionUtil {
        @Override
        public void close(Connection connection){
            if(connection != null){
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
            }
        }
    }

}
