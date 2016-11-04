package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PatronDaoImpl implements PatronDao {
    private static final Logger logger = LoggerFactory.getLogger(PatronDaoImpl.class);
    private JdbcOperations jdbcOperations;

    @Autowired
    PatronDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    /**
     *
     * Returns patron data transfer object using patrons email. Useful to check if patron exists in database prior to
     * insertion.
     */
    @Transactional
    @Override
    public PatronDto getPatron(String email){
        String query = "SELECT * FROM patron WHERE email = ?";
        PatronDto dto = null;
        try{
            dto = jdbcOperations.queryForObject(query, ((rs, rowNum) -> {
                return new PatronDto(rs.getInt("patronid"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getInt("zipcode"),
                        rs.getString("streetaddress"),
                        rs.getTimestamp("joindate"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getShort("remotelibrary") == 1,
                        rs.getString("password"));
            }), email);
        } catch (EmptyResultDataAccessException ex){
            logger.debug("Email {} not found in patron table", email);
        }
         return dto;
    }


    /**
     *
     * Returns patron using the itemReturnOutput object. Part of multiple steps in the item return process
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public PatronDto getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String query = "SELECT * FROM patron WHERE patronid = ?";
        return  jdbcOperations.queryForObject(query, ((rs, rowNum) -> {
            return new PatronDto(rs.getInt("patronid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getInt("zipcode"),
                    rs.getString("streetaddress"),
                    rs.getTimestamp("joindate"),
                    rs.getString("email"),
                    rs.getLong("phone"),
                    rs.getShort("remotelibrary") == 1,
                    rs.getString("password"));
        }), itemReturnOutput.getPatronid());
    }

    /**
     *
     * Used to insert patron using the patronDto transfer object
     */
    @Transactional
    @Override
    public boolean insertPatron(PatronDto patron) {
        String insert = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
                "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        jdbcOperations.update(insert,
                              patron.getFirstname(),
                              patron.getLastname(),
                              patron.getCity(),
                              patron.getState(),
                              patron.getZipcode(),
                              patron.getStreetAddress(),
                              patron.getJoinDate(),
                              patron.getPhone(),
                              patron.getPassword(),
                              patron.isRemotelibrary(),
                              patron.getEmail());
        return true;
    }



//    @Override
//    public boolean insertPatron(PatronDto patron) {
//        Connection connection = connectionFactory.getConnection();
//        PreparedStatement preparedStatement = null;
//        String insertStmt = "INSERT INTO patron(firstname, lastname, city, state, zipcode, streetaddress, joindate, " +
//                "phone, password, remotelibrary, email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
//        try {
//            preparedStatement = connection.prepareStatement(insertStmt);
//            preparedStatement.setString(1,patron.getFirstname());
//            preparedStatement.setString(2,patron.getLastname());
//            preparedStatement.setString(3,patron.getCity());
//            preparedStatement.setString(4, patron.getState());
//            preparedStatement.setInt(5,patron.getZipcode());
//            preparedStatement.setString(6, patron.getStreetAddress());
//            preparedStatement.setTimestamp(7, patron.getJoinDate());
//            preparedStatement.setLong(8,patron.getPhone());
//            preparedStatement.setString(9, patron.getPassword());
//            preparedStatement.setBoolean(10, patron.isRemotelibrary());
//            preparedStatement.setString(11,patron.getEmail());
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            logger.error("SQL error when inserting patron with email {}", patron.getEmail(), e);
//            throw new IllegalStateException("SQL error inserting patron. See log for details");
//        } finally {
//            connectionUtil.close(connection);
//            connectionUtil.close(preparedStatement);
//        }
//        return true;
//    }



}
