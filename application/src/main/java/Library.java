import thompson.library.system.daos.DaoManager;
import thompson.library.system.daos.DaoManagerFactory;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionManager;
import thompson.library.system.utilities.HsqlConnectionFactory;

import java.sql.*;


public class Library {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = ConnectionManager.getConnectionFactory();
        try(Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement()) {

            try(ResultSet resultSet =  statement.executeQuery("SELECT * FROM book")){
                while(resultSet.next()){
                    System.out.println(resultSet.getString("TITLE"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
   }
}
