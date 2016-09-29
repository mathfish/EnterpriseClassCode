import thompson.library.system.utilities.DerbyConnectionFactory;
import java.sql.*;


public class Library {
    public static void main(String[] args) {
        DerbyConnectionFactory derbyConnectionFactory = new DerbyConnectionFactory();
        try(Connection connection = derbyConnectionFactory.getConnection();
            Statement statement = connection.createStatement()) {

            try(ResultSet resultSet =  statement.executeQuery("SELECT * FROM PATRON")){
                while(resultSet.next()){
                    System.out.println(resultSet.getString("TITLE"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
   }
}
