package thompson.library.system.utilities;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HsqlConnectionFactoryTest {
    @Test // test gets valid connection
    public void getConnectionTest(){
    HsqlConnectionFactory impl = new HsqlConnectionFactory();
        Connection connection = impl.getConnection();
        assertNotNull(connection);
        try {
            assertTrue(connection.isValid(5));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
