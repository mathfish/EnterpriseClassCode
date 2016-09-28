package thompson.library.system.utilities;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jonathanthompson on 9/27/16.
 */
public class DerbyConnectionFactory{
    private static DerbyConnectionFactory connection = new DerbyConnectionFactory();
    private static final String url;
    static {
        File file = new File("../database/javadb/librarydb");
        String dbPath = file.getAbsoluteFile().getAbsolutePath();
        url = "jdbc:derby:" + dbPath;
    }


    private DerbyConnectionFactory(){
    }

    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return connection.createConnection();
    }

}
