package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.NonUniqueResultException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DerbyPatronDao implements PatronDao {

    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    DerbyPatronDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    @Override
    public PatronDto getPatron(String email) throws NonUniqueResultException{
        Connection connection = connectionFactory.getConnection();
        ResultSet resultSet = null;
        PatronDto patronDTO = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM patron WHERE email = ?");
            preparedStatement.setString(1,email);
            resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                patronDTO = new PatronDto(resultSet.getInt("patronid"),
                                          resultSet.getString("firstname"),
                                          resultSet.getString("lastname"),
                                          resultSet.getString("city"),
                                          resultSet.getString("state"),
                                          resultSet.getInt("zipcode"),
                                          resultSet.getString("streetaddress"),
                                          resultSet.getTimestamp("joindate"),
                                          resultSet.getString("email"),
                                          resultSet.getLong("phone"),
                                          resultSet.getShort("remotelibrary") == 1,
                                          resultSet.getString("password"));
            }

            if(resultSet.next()){
                throw new NonUniqueResultException("Patron with email " + email + "returns multiple rows in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.close(connection);
            connectionUtil.close(resultSet);
            connectionUtil.close(preparedStatement);
        }

        return patronDTO;
    }

    @Override
    public boolean insertPatron(PatronDto patron) {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String insertStmt = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertStmt);
            preparedStatement.setString(1,patron.getFirstname());
            preparedStatement.setString(2,patron.getLastname());
            preparedStatement.setString(3,patron.getCity());
            preparedStatement.setString(4, patron.getState());
            preparedStatement.setInt(5,patron.getZipcode());
            preparedStatement.setString(6, patron.getStreetAddress());
            preparedStatement.setTimestamp(7, patron.getJoinDate());
            preparedStatement.setLong(8,patron.getPhone());
            preparedStatement.setString(9, patron.getPassword());
            preparedStatement.setBoolean(10, patron.isRemotelibrary());
            preparedStatement.setString(11,patron.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.close(connection);
            connectionUtil.close(preparedStatement);
        }
        return true;
    }
}
