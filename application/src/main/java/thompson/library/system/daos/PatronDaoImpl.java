package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.PatronDto;

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
}
