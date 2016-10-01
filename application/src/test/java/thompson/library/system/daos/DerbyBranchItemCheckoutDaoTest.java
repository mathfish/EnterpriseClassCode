package thompson.library.system.daos;


import org.junit.Test;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DerbyBranchItemCheckoutDaoTest {
    private Connection connection;
    private java.sql.Date date;
    private int returnedTtl = 0;

    public DerbyBranchItemCheckoutDaoTest() {}

    private Connection getLocalConnection(){
        DerbyConnectionFactory derbyConnectionFactory = new DerbyConnectionFactory();
        this.connection = derbyConnectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        return connection;
    }

    @Test
    public void testGetBranchItemCheckout(){
        DerbyBranchItemCheckoutDao impl = new DerbyBranchItemCheckoutDao(new TestConnectionFactory(), new TestConnectionUtil2());
        BranchItemDto branchItemDto = mock(BranchItemDto.class);
        when(branchItemDto.getBranchitemid()).thenReturn(15);
        BranchItemCheckoutDto branchItemCheckoutDto = impl.getBranchItemCheckout(branchItemDto);
        assertEquals(15,branchItemCheckoutDto.getBranchItemID().intValue());
        assertEquals(3,branchItemCheckoutDto.getCheckoutID().intValue());
        assertFalse(branchItemCheckoutDto.isOverdue());
        assertFalse(branchItemCheckoutDto.isRenew());
        assertFalse(branchItemCheckoutDto.isReturned());
        assertEquals(date.toString(), branchItemCheckoutDto.getDueDate().toString());
    }


    @Test
    public void testUpdateBranchItemCheckout(){
        DerbyBranchItemCheckoutDao impl = new DerbyBranchItemCheckoutDao(new TestConnectionFactory(), new ConnectionUtil());
        Calendar calendar = Calendar.getInstance();
        date = new java.sql.Date(calendar.getTime().getTime());
        BranchItemCheckoutDto branchItemCheckoutDto = mock(BranchItemCheckoutDto.class);
        when(branchItemCheckoutDto.getBranchItemID()).thenReturn(15);
        when(branchItemCheckoutDto.getCheckoutID()).thenReturn(3);
        when(branchItemCheckoutDto.getReturnDate()).thenReturn(date);
        when(branchItemCheckoutDto.isOverdue()).thenReturn(true);
        when(branchItemCheckoutDto.isReturned()).thenReturn(true);

        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = impl.updateBranchItemCheckout(branchItemCheckoutDto);
        assertEquals(15,itemReturnOutput.getBranchitemid().intValue());
        assertEquals(3, itemReturnOutput.getCheckoutid().intValue());
        String query = "SELECT * FROM branchitemcheckout WHERE branchitemid = 15 AND checkoutid = 3";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            assertFalse(itemReturnOutput.getConnection().isClosed());
            preparedStatement = itemReturnOutput.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                assertEquals(date.toString(),resultSet.getDate("returndate").toString());
                assertTrue(resultSet.getBoolean("overdue"));
                assertTrue(resultSet.getBoolean("returned"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            TestConnectionUtil2 util = new TestConnectionUtil2();
            util.close(itemReturnOutput.getConnection());
            util.close(preparedStatement);
            util.close(resultSet);
        }
    }

    @Test
    public void testGetNumberOfItemsReturnedFromCheckout(){
        TestConnectionUtil2 util = new TestConnectionUtil2();
        DerbyBranchItemCheckoutDao impl = new DerbyBranchItemCheckoutDao(new DerbyConnectionFactory(), util);
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getCheckoutid()).thenReturn(3);
        TestConnectionFactory2 factory = new TestConnectionFactory2();

        when(itemReturnOutput.getConnection()).thenReturn(factory.getConnection());
        assertEquals(returnedTtl,impl.getNumberOfItemsReturnedFromCheckout(itemReturnOutput));
        util.close(connection);
    }



    private class TestConnectionFactory extends DerbyConnectionFactory {
        @Override
        public Connection getConnection() {
            Connection connection = super.getConnection();
            String insert = "INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, renew, " +
                    "returned) VALUES (?,?,?,?,?,?)";
            String query = "SELECT COUNT(*) FROM branchitemcheckout WHERE checkoutid = 3 AND returned = true";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Calendar calendar = Calendar.getInstance();
            date = new java.sql.Date(calendar.getTime().getTime());
            try{
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setInt(1,15);
                preparedStatement.setInt(2,3);
                preparedStatement.setBoolean(3, false);
                preparedStatement.setDate(4,date);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    returnedTtl = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                assertTrue(false);
            } finally {
                TestConnectionUtil2 util = new TestConnectionUtil2();
                util.close(preparedStatement);
                util.close(resultSet);
            }

            return connection;
        }

    }

    private class TestConnectionFactory2 extends DerbyConnectionFactory {
        @Override
        public Connection getConnection() {
            Connection connection = getLocalConnection();
            String insert = "INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, renew, " +
                    "returned) VALUES (?,?,?,?,?,?)";
            String query = "SELECT COUNT(*) FROM branchitemcheckout WHERE checkoutid = 3 AND returned = true";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Calendar calendar = Calendar.getInstance();
            date = new java.sql.Date(calendar.getTime().getTime());
            try{
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setInt(1,15);
                preparedStatement.setInt(2,3);
                preparedStatement.setBoolean(3, false);
                preparedStatement.setDate(4,date);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    returnedTtl = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                assertTrue(false);
            } finally {
                TestConnectionUtil2 util = new TestConnectionUtil2();
                util.close(preparedStatement);
                util.close(resultSet);
            }

            return connection;
        }

    }


    private class TestConnectionUtil2 extends ConnectionUtil{
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
