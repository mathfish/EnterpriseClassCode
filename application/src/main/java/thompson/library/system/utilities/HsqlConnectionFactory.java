package thompson.library.system.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class HsqlConnectionFactory implements ConnectionFactory {
    private  final String url;

    HsqlConnectionFactory(){
        File propfile = new File("database.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(properties.getProperty("hsqlDbUrl"));
        String dbPath = file.getAbsoluteFile().getAbsolutePath();
        url = "jdbc:hsqldb:file:" + dbPath;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
