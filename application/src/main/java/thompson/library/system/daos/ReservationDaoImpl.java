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
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;
    private JdbcOperations jdbcOperations;

    @Autowired
    ReservationDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }
//    ReservationDaoImpl(@Value("#{T(thompson.library.system.utilities.ConnectionManager).getConnectionFactory()}")
//                               ConnectionFactory connectionFactory,
//                       ConnectionUtil connectionUtil){
//        this.connectionFactory = connectionFactory;
//        this.connectionUtil = connectionUtil;
//    }

    /**
     *
     * Used to mark a reservation as being fulfilled, if necessary, as part of many steps of the return process
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public ReservationDto fulfillReservation(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {



        String query = "SELECT * FROM reservation WHERE branchitemid = ? AND fulfilled = false ORDER BY reservdate";
        String update = "UPDATE reservation SET fulfilled = ? WHERE reservationid = ?";


        List<ReservationDto> dtos = jdbcOperations.query(query, (rs, rowNum) -> {
            return new ReservationDto(rs.getInt("reservationid"),
                    rs.getInt("patronid"),
                    rs.getInt("branchitemid"),
                    rs.getDate("reservdate"),
                    rs.getBoolean("fulfilled"),
                    rs.getInt("forbranchid"));
            }, itemReturnOutput.getBranchitemid());

        if(!dtos.isEmpty()){
            jdbcOperations.update(update, true, dtos.get(0).getReservationid());
            ReservationDto dto = dtos.get(0);
            dto.setFulfilled(true);
            return dto;
        } else {
            return null;
        }


//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        ReservationDto reservationDto = null;
//        try{
//            Connection connection = itemReturnOutput.getConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1,itemReturnOutput.getBranchitemid());
//            resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                preparedStatement = connection.prepareStatement(update);
//                preparedStatement.setBoolean(1,true);
//                preparedStatement.setInt(2,resultSet.getInt("reservationid"));
//                preparedStatement.executeUpdate();
//
//                reservationDto = new ReservationDto(resultSet.getInt("reservationid"),
//                        resultSet.getInt("patronid"), resultSet.getInt("branchitemid"),
//                        resultSet.getDate("reservdate"), true,
//                        resultSet.getInt("forbranchid"));
//            }
//        } catch (SQLException e) {
//            logger.error("SQL error checking reservation for branchitemid {}", itemReturnOutput.getBranchitemid(), e);
//            throw new IllegalStateException("SQL error in checking reservation. See log for details");
//        } finally {
//            connectionUtil.close(preparedStatement);
//            connectionUtil.close(resultSet);
//        }
//        return reservationDto;
    }

}
