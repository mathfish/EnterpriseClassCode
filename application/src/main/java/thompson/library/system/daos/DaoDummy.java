package thompson.library.system.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.PatronDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DaoDummy {
    private JdbcOperations jdbcOperations;
    private String findAllPatrons = "SELECT * FROM patron";

    @Autowired
    DaoDummy(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    @Transactional
    public List<PatronDto> getAllPatrons(){
        return jdbcOperations.query(findAllPatrons, (rs, rowNum) -> {
            return new PatronDto(rs.getInt("patronid"),
                                 rs.getString("firstname"),
                                 rs.getString("lastname"),
                                 "city",
                                 "state",
                                 55555,
                                 "address",
                                 null,
                                 rs.getString("email"),
                                 1111111111,
                                 false,
                                 "pword");
        });
    }

    @Transactional
    public List<PatronDto> getAllPatronsByFirstName(String fname){
        String query = "SELECT * FROM patron where firstname = ?";
        return jdbcOperations.query(query, (rs, rowNum) -> {
            return new PatronDto(rs.getInt("patronid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    "city",
                    "state",
                    55555,
                    "address",
                    null,
                    rs.getString("email"),
                    1111111111,
                    false,
                    "pword");
        }, fname);
    }


}
