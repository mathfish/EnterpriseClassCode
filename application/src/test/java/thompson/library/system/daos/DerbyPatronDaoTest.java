package thompson.library.system.daos;

import org.junit.Test;
import thompson.library.system.dtos.PatronDto;

import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.DerbyConnectionFactory;
import thompson.library.system.utilities.NonUniqueResultException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DerbyPatronDaoTest {
    private Connection connection;

    public DerbyPatronDaoTest(){
        DerbyConnectionFactory derbyConnectionFactory = new DerbyConnectionFactory();
        this.connection = derbyConnectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test // Test if insertion into javadb database is working
    public void insertPatronTest(){
        DerbyConnectionFactory derbyConnectionFactory = mock(DerbyConnectionFactory.class);
        when(derbyConnectionFactory.getConnection()).thenReturn(connection);
        DerbyPatronDao impl = new DerbyPatronDao(derbyConnectionFactory, new TestConnectionUtil1());
        impl.insertPatron(getDto());
    }


    @Test // Test if reading database row is correct
    public void getPatronTest(){
        DerbyPatronDao impl = new DerbyPatronDao(new TestConnectionFactory(), new TestConnectionUtil2());
        try {
            PatronDto dto = impl.getPatron("test2@email.test");
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

        } catch (NonUniqueResultException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    private PatronDto getDto(){
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        return new PatronDto("testFirst", "testLast", "testCity", "ST", 99999,
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
    private class TestConnectionFactory extends DerbyConnectionFactory {
        @Override
        public Connection getConnection() {
            Connection connection = super.getConnection();
            String insertStmt = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                    "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(insertStmt);
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
            }
            return connection;
        }

    }
}
