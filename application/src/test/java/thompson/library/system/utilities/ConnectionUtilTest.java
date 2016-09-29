package thompson.library.system.utilities;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathanthompson on 9/28/16.
 */
public class ConnectionUtilTest {

    @Test // test close connection
    public void closeConnectionTest(){
        ConnectionUtil impl = new ConnectionUtil();
        ConnectionFactory connectionFactory = new DerbyConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        try {
            assertFalse(connection.isClosed());
            impl.close(connection);
            assertTrue(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test // test statement closed
    public void closeStatementTest(){
        ConnectionUtil impl = new ConnectionUtil();
        ConnectionFactory connectionFactory = new DerbyConnectionFactory();
        try(Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOOK");
            assertFalse(preparedStatement.isClosed());
            impl.close(preparedStatement);
            assertTrue(preparedStatement.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test // test resultSet closed
    public void closeResultSetTest(){
        ConnectionUtil impl = new ConnectionUtil();
        ConnectionFactory connectionFactory = new DerbyConnectionFactory();
        try(Connection connection = connectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOOK")){
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.isClosed());
            impl.close(resultSet);
            assertTrue(resultSet.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
