package thompson.library.system.daos;

import org.junit.Test;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DerbyCheckoutDaoTest {

    private Connection connection;
    private java.sql.Timestamp date;
    private int checkoutid;

    private Connection getLocalConnection(){
        DerbyConnectionFactory derbyConnectionFactory = new DerbyConnectionFactory();
        connection = derbyConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Calendar calendar = Calendar.getInstance();
        date = new java.sql.Timestamp(calendar.getTime().getTime());
        String insert = "INSERT INTO checkout(patronid, checkoutdate, numberofitems, overdue, itemsreturned)" +
                " VALUES(1, ?, ?, false, false)";

        String query = "SELECT checkoutid FROM checkout WHERE patronid = 1 AND checkoutdate = ? AND numberofitems = ? " +
                "AND overdue = false AND itemsreturned = false";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setTimestamp(1,date);
            preparedStatement.setInt(2,1);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1,date);
            preparedStatement.setInt(2,1);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                checkoutid =resultSet.getInt(1);
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
    public void updateCheckoutTest(){
        TestConnectionUtil util = new TestConnectionUtil();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getConnection()).thenReturn(getLocalConnection());
        when(itemReturnOutput.isReturned()).thenReturn(true);
        when(itemReturnOutput.getCheckoutid()).thenReturn(checkoutid);
        DerbyCheckoutDao impl = new DerbyCheckoutDao(new DerbyConnectionFactory(), util);
        impl.updateCheckout(itemReturnOutput);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            String query = "SELECT itemsreturned FROM checkout WHERE checkoutid = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,checkoutid);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                assertTrue(resultSet.getBoolean(1));
            }
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            util.close(connection);
            util.close(preparedStatement);
            util.close(resultSet);
        }
    }

    @Test
    public void getCheckoutTest(){
        TestConnectionUtil util = new TestConnectionUtil();
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getConnection()).thenReturn(getLocalConnection());
        when(itemReturnOutput.getCheckoutid()).thenReturn(checkoutid);
        DerbyCheckoutDao impl = new DerbyCheckoutDao(new DerbyConnectionFactory(), util);
        CheckoutDto dto = impl.getCheckout(itemReturnOutput);
        assertEquals(date.toString(),dto.getCheckoutdate().toString());
        assertEquals(1,dto.getPatronid());
        assertEquals(1,dto.getNumberofitems());
        assertFalse(dto.isOverdue());
        assertFalse(dto.isItemsreturned());
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
