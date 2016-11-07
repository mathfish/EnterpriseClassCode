package thompson.library.system.daos;

import org.junit.Test;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionManager;
import thompson.library.system.utilities.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BranchItemDaoImplTest {

    private Connection connection;

    public BranchItemDaoImplTest() {}

    private Connection getConnection(){
        ConnectionFactory connectionFactory = ConnectionManager.getConnectionFactory();
        this.connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        return connection;
    }


    @Test
    public void testUpdateBranchItem() {
//        BranchItemDaoImpl impl = new BranchItemDaoImpl(null, new ConnectionUtil());
//        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
//        when(itemReturnOutput.getConnection()).thenReturn(getConnection());
//        when(itemReturnOutput.isReserved()).thenReturn(false);
//
//        String insert = "INSERT INTO branchitem(branchid, checkedout, reserved, intransit, currentlocation" +
//                ") VALUES (?,?,?,?,?)";
//
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection.setAutoCommit(false);
//            preparedStatement = connection.prepareStatement(insert);
//            preparedStatement.setInt(1, 1);
//            preparedStatement.setBoolean(2, true);
//            preparedStatement.setBoolean(3, true);
//            preparedStatement.setBoolean(4, true);
//            preparedStatement.setInt(5, 1);
//            preparedStatement.executeUpdate();
//
//            //impossible state for testing. Cannot be checked out and reserved at same time
//            preparedStatement = connection.prepareStatement("SELECT branchitemid FROM branchitem WHERE" +
//                    " branchid =1 AND checkedout = true AND reserved = true AND intransit = true AND currentlocation = 1");
//
//            resultSet = preparedStatement.executeQuery();
//            int branchitemid = 0;
//            if (resultSet.next()) {
//                branchitemid = resultSet.getInt(1);
//            }
//
//            when(itemReturnOutput.getBranchitemid()).thenReturn(branchitemid);
//            impl.updateBranchItem(itemReturnOutput);
//
//
//            preparedStatement = connection.prepareStatement("SELECT * FROM branchitem WHERE branchitemid = ?");
//            preparedStatement.setInt(1, branchitemid);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                assertFalse(resultSet.getBoolean("checkedout"));
//                assertFalse(resultSet.getBoolean("reserved"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            assertTrue(false);
//        } finally {
//            TestConnectionUtil connectionUtil = new TestConnectionUtil();
//            connectionUtil.close(connection);
//            connectionUtil.close(preparedStatement);
//            connectionUtil.close(resultSet);
//        }

    }


    // Utility that rolls back changes before closing connection
    private class TestConnectionUtil extends ConnectionUtil{
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
