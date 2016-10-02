package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PatronDaoImpl implements PatronDao {
    private static final Logger logger = LoggerFactory.getLogger(PatronDaoImpl.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    PatronDaoImpl(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    /**
     *
     * Returns patron data transfer object using patrons email. Useful to check if patron exists in database prior to
     * insertion.
     */
    @Override
    public PatronDto getPatron(String email){
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
        } catch (SQLException e) {
            logger.error("SQL exception for getting patron with email {}", email, e);
            throw new IllegalStateException("SQL error when getting patron. See log for details");
        } finally {
            connectionUtil.close(connection);
            connectionUtil.close(resultSet);
            connectionUtil.close(preparedStatement);
        }

        return patronDTO;
    }

    /**
     *
     * Returns patron using the itemReturnOutput object. Part of multiple steps in the item return process
     */
    @Override
    public PatronDto getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String query = "SELECT * FROM patron WHERE patronid = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PatronDto patronDto = null;
        try{
            Connection connection = itemReturnOutput.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,itemReturnOutput.getPatronid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                patronDto = new PatronDto(resultSet.getInt("patronid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getInt("zipcode"),
                        resultSet.getString("streetaddress"),
                        resultSet.getTimestamp("joindate"),
                        resultSet.getString("email"),
                        resultSet.getLong("phone"),
                        resultSet.getBoolean("remotelibrary"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            logger.error("SQL exception for getting patron with id {}", itemReturnOutput.getPatronid(), e);
            throw new IllegalStateException("SQL error when getting patron. See log for details");
        } finally {
            connectionUtil.close(preparedStatement);
            connectionUtil.close(resultSet);
        }
        return patronDto;
    }

    /**
     *
     * Used to insert patron using the patronDto transfer object
     */
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
            logger.error("SQL error when inserting patron with email {}", patron.getEmail(), e);
            throw new IllegalStateException("SQL error inserting patron. See log for details");
        } finally {
            connectionUtil.close(connection);
            connectionUtil.close(preparedStatement);
        }
        return true;
    }



}
