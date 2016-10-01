package thompson.library.system.daos;

import org.junit.Test;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DerbyBranchItemDaoTest {

    private Connection connection;
    private DerbyBranchItemDao impl;
    private java.sql.Date date;
    public DerbyBranchItemDaoTest(){
        DerbyConnectionFactory derbyConnectionFactory = new DerbyConnectionFactory();
        this.connection = derbyConnectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test // Integration test to test returnItem and setIfReserved Methods when item is reserved
    public void returnReservedItemTest(){
        BranchItemDto branchItemDto = mock(BranchItemDto.class);
        PatronDto patronDto = mock(PatronDto.class);
        DerbyConnectionFactory derbyConnectionFactory = mock(DerbyConnectionFactory.class);
        when(derbyConnectionFactory.getConnection()).thenReturn(connection);
        TestConnectionUtil util = new TestConnectionUtil();
        impl = new DerbyBranchItemDao(new TestConnectionFactory(), util);

        when(patronDto.getPatronid()).thenReturn(Optional.of(1));
        when(branchItemDto.getBranchitemid()).thenReturn(15);
        BranchItemDao.ReturnItemOutput output = impl.returnItem(branchItemDto,patronDto);

        try {
            assertFalse(output.connection.get().isClosed());
            assertTrue(output.fulfillReturn);
            int pid = output.getPatronid().get();
            assertEquals(1, pid);
            int bid = output.getBranchitemid().get();
            assertEquals(15,bid);
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        util.close(connection);
    }

    @Test // Integration test to test returnItem and setIfReserved Methods when item is not reserved
    public void returnNonReservedItemTest(){
        BranchItemDto branchItemDto = mock(BranchItemDto.class);
        PatronDto patronDto = mock(PatronDto.class);
        DerbyConnectionFactory derbyConnectionFactory = mock(DerbyConnectionFactory.class);
        when(derbyConnectionFactory.getConnection()).thenReturn(connection);
        TestConnectionUtil2 util = new TestConnectionUtil2();
        impl = new DerbyBranchItemDao(new TestConnectionFactory2(), util);

        when(patronDto.getPatronid()).thenReturn(Optional.of(1));
        when(branchItemDto.getBranchitemid()).thenReturn(15);
        BranchItemDao.ReturnItemOutput output = impl.returnItem(branchItemDto,patronDto);

        try {
            assertFalse(output.connection.get().isClosed());
            assertFalse(output.fulfillReturn);
            assertFalse(output.getPatronid().isPresent());
            assertFalse(output.getBranchitemid().isPresent());
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        util.close(connection);
    }



    private class TestConnectionFactory extends DerbyConnectionFactory {
        @Override
        public Connection getConnection() {
            String insertBIC = "INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, renew, " +
                    "returned) VALUES (?,?,?,?,?,?)";
            String insertBI = "UPDATE branchitem SET checkedout = false WHERE branchitemid = 15";

            String insertR = "INSERT INTO reservation (patronid, forbranchid, branchitemid, reservdate, fulfilled)" +
                    "VALUES(1,1,15,?, false)";
            Calendar calendar = Calendar.getInstance();
            date = new java.sql.Date(calendar.getTime().getTime());
            PreparedStatement preparedStatement;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insertBIC);
                preparedStatement.setInt(1,15);
                preparedStatement.setInt(2,3);
                preparedStatement.setBoolean(3, false);
                preparedStatement.setDate(4,date);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(insertBI);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(insertR);
                preparedStatement.setDate(1,date);
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                assertTrue(false);
            }
            return connection;
        }

    }


    private class TestConnectionFactory2 extends DerbyConnectionFactory {
        @Override
        public Connection getConnection() {
            String insertBIC = "INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, renew, " +
                    "returned) VALUES (?,?,?,?,?,?)";
            String insertBI = "UPDATE branchitem SET checkedout = false WHERE branchitemid = 15";
            Calendar calendar = Calendar.getInstance();
            date = new java.sql.Date(calendar.getTime().getTime());
            PreparedStatement preparedStatement;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insertBIC);
                preparedStatement.setInt(1,15);
                preparedStatement.setInt(2,3);
                preparedStatement.setBoolean(3, false);
                preparedStatement.setDate(4,date);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(insertBI);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                assertTrue(false);
            }
            return connection;
        }
    }

    private class TestConnectionUtil extends ConnectionUtil{
        @Override
        public void close(Connection connection){
            if(connection != null){
                try {
                    String checkBI = "SELECT * FROM branchitem WHERE branchitemid = 15";
                    String checkBIC = "SELECT * FROM branchitemcheckout WHERE branchitemid = 15 AND checkoutid = 3 AND duedate = ?";


                    PreparedStatement preparedStatement = connection.prepareStatement(checkBI);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        assertFalse(resultSet.getBoolean("checkedout"));
                        assertTrue(resultSet.getBoolean("reserved"));
                    }

                    preparedStatement = connection.prepareStatement(checkBIC);
                    preparedStatement.setDate(1,date);
                    resultSet = preparedStatement.executeQuery();
                    Calendar cal = Calendar.getInstance();
                    Calendar calReturn = Calendar.getInstance();
                    cal.setTime(date);
                    if(resultSet.next()){
                        assertTrue(resultSet.getBoolean("returned"));
                        calReturn.setTime(resultSet.getDate("returnDate"));
                        assertEquals(cal.get(Calendar.DAY_OF_MONTH), calReturn.get(Calendar.DAY_OF_MONTH));
                        assertEquals(cal.get(Calendar.YEAR), calReturn.get(Calendar.YEAR));
                    }

                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
            }
        }
    }

    private class TestConnectionUtil2 extends ConnectionUtil{
        @Override
        public void close(Connection connection){
            if(connection != null){
                String checkBI = "SELECT * FROM branchitem WHERE branchitemid = ?";
                String checkBIC = "SELECT returned, returndate FROM branchitemcheckout WHERE branchitemid = 15 AND checkoutid = 3 AND duedate = ?";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(checkBI);
                    preparedStatement.setInt(1,15);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        assertFalse(resultSet.getBoolean("checkedout"));
                        assertFalse(resultSet.getBoolean("reserved"));
                    }

                    preparedStatement = connection.prepareStatement(checkBIC);
                    preparedStatement.setDate(1,date);
                    resultSet = preparedStatement.executeQuery();
                    Calendar cal = Calendar.getInstance();
                    Calendar calReturn = Calendar.getInstance();
                    cal.setTime(date);
                    if(resultSet.next()){
                        assertTrue(resultSet.getBoolean("returned"));
                        calReturn.setTime(resultSet.getDate("returnDate"));
                        assertEquals(cal.get(Calendar.DAY_OF_MONTH), calReturn.get(Calendar.DAY_OF_MONTH));
                        assertEquals(cal.get(Calendar.YEAR), calReturn.get(Calendar.YEAR));
                    }

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
