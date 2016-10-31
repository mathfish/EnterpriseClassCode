package thompson.library.system.daos;

import org.junit.Test;
import thompson.library.system.dtos.Dto;

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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatronDaoImplTest {
    private Connection connection;

    public PatronDaoImplTest(){}

    private Connection getConnection(){
        ConnectionFactory connectionFactory = ConnectionManager.getConnectionFactory();
        this.connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    @Test // Test if insertion into javadb database is working
    public void insertPatronTest(){
        DerbyConnectionFactory derbyConnectionFactory = mock(DerbyConnectionFactory.class);
        when(derbyConnectionFactory.getConnection()).thenReturn(getConnection());
        PatronDaoImpl impl = new PatronDaoImpl(derbyConnectionFactory, new TestConnectionUtil1());
        impl.insertPatron(getDto());
    }


    @Test // Test if reading database row is correct
    public void getPatronTestByEmail(){
        PatronDaoImpl impl = new PatronDaoImpl(new TestConnectionFactory(), new TestConnectionUtil2());

            Dto dto = impl.getPatron("test2@email.test");
            assertEquals("testFirst2", dto.getFirstname());
            assertEquals("testLast2", dto.getLastname());
            assertEquals("testCity2",dto.getCity());
            assertEquals("TT",dto.getState());
            assertEquals(88888,dto.getZipcode());
            assertEquals("testStreetAddress2",dto.getStreetAddress());
            assertEquals("test2@email.test",dto.getEmail());
            assertEquals(1111111111L,dto.getPhone());
            assertEquals(true, dto.isRemotelibrary());
            assertEquals("testPW2",dto.getPassword());
    }

    @Test
    public void getPatronByItemReturnOutput(){
        PatronDaoImpl impl = new PatronDaoImpl(null, new ConnectionUtil());
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput= mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.getConnection()).thenReturn(getConnection());
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String insert = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String query = "SELECT patronid FROM patron WHERE email = ?";
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        int patronidAns = 0;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, "testFirst2");
            preparedStatement.setString(2, "testLast2");
            preparedStatement.setString(3, "testCity2");
            preparedStatement.setString(4, "TT");
            preparedStatement.setInt(5, 88888);
            preparedStatement.setString(6, "testStreetAddress2");
            preparedStatement.setTimestamp(7, joinDate);
            preparedStatement.setLong(8, 1111111111L);
            preparedStatement.setString(9, "testPW2");
            preparedStatement.setBoolean(10, true);
            preparedStatement.setString(11, "test2@email.test");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,"test2@email.test");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                patronidAns = resultSet.getInt(1);
            }
            when(itemReturnOutput.getPatronid()).thenReturn(patronidAns);
            Dto dto = impl.getPatron(itemReturnOutput);
            assertFalse(connection.isClosed());
            int pid = dto.getPatronid().get();
            assertEquals(patronidAns, pid);
            assertEquals("testFirst2", dto.getFirstname());
            assertEquals("testLast2", dto.getLastname());
            assertEquals("testCity2", dto.getCity());
            assertEquals("TT", dto.getState());
            assertEquals(88888, dto.getZipcode());
            assertEquals("testStreetAddress2", dto.getStreetAddress());
            assertEquals(joinDate, dto.getJoinDate());
            assertEquals(1111111111L, dto.getPhone());
            assertEquals("testPW2", dto.getPassword());
            assertEquals("test2@email.test", dto.getEmail());
            assertEquals(true, dto.isRemotelibrary());
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            ConnectionUtil util = new TestConnectionUtil2();
            util.close(connection);
            util.close(preparedStatement);
            util.close(resultSet);
        }


    }

    private Dto getDto(){
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        return new Dto("testFirst", "testLast", "testCity", "ST", 99999,
                "testStreetAddress",joinDate, "test@email.test", 9999999999L,false, "testPW");
    }

    //Connection utility that verifies insert, then rolls back changes, and closes connection
    private class TestConnectionUtil1 extends ConnectionUtil {
        @Override
        public void close(Connection connection){
            if(connection != null){
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patron WHERE email = ?");
                    preparedStatement.setString(1,"test@email.test");
                    ResultSet resultSet =  preparedStatement.executeQuery();
                    if(resultSet.next()){
                        assertEquals("testFirst", resultSet.getString("FIRSTNAME"));
                        assertEquals("testLast", resultSet.getString("LASTNAME"));
                        assertEquals("testCity",resultSet.getString("city"));
                        assertEquals("ST",resultSet.getString("state"));
                        assertEquals(99999,resultSet.getInt("zipcode"));
                        assertEquals("testStreetAddress",resultSet.getString("streetaddress"));
                        assertEquals("test@email.test",resultSet.getString("email"));
                        assertEquals(9999999999L,resultSet.getLong("phone"));
                        assertEquals(true,resultSet.getShort("remotelibrary") == 0);
                        assertEquals("testPW",resultSet.getString("password"));
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

    // Utility that rolls back changes before closing connection
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

    //Connection facility that inserts before so that read test has consistent data
    private class TestConnectionFactory implements ConnectionFactory {
        @Override
        public Connection getConnection() {
            Connection connection = ConnectionManager.getConnectionFactory().getConnection();
            String insertStmt = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                    "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insertStmt);
                preparedStatement.setString(1, "testFirst2");
                preparedStatement.setString(2, "testLast2");
                preparedStatement.setString(3, "testCity2");
                preparedStatement.setString(4, "TT");
                preparedStatement.setInt(5, 88888);
                preparedStatement.setString(6, "testStreetAddress2");
                preparedStatement.setTimestamp(7, joinDate);
                preparedStatement.setLong(8, 1111111111L);
                preparedStatement.setString(9, "testPW2");
                preparedStatement.setBoolean(10, true);
                preparedStatement.setString(11, "test2@email.test");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                assertTrue(false);
            } finally {
                TestConnectionUtil2 util = new TestConnectionUtil2();
                util.close(preparedStatement);
            }
            return connection;
        }

    }
}
