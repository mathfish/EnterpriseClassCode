import thompson.library.system.utilities.DerbyConnectionFactory;
import java.sql.*;


public class Library {
    public static void main(String[] args) {

        try(Connection connection = DerbyConnectionFactory.getConnection();
            Statement statement = connection.createStatement()) {

            try(ResultSet resultSet =  statement.executeQuery("SELECT * FROM BOOK")){
                while(resultSet.next()){
                    System.out.println(resultSet.getString("TITLE"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
   }
}
