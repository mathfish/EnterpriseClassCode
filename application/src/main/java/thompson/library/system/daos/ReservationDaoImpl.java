package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReservationDaoImpl implements ReservationDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    @Autowired
    ReservationDaoImpl(@Value("#{T(thompson.library.system.utilities.ConnectionManager).getConnectionFactory()}")
                               ConnectionFactory connectionFactory,
                       ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    /**
     *
     * Used to mark a reservation as being fulfilled, if necessary, as part of many steps of the return process
     */
    @Override
    public ReservationDto fulfillReservation(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String query = "SELECT * FROM reservation WHERE branchitemid = ? AND fulfilled = false ORDER BY reservdate";
        String update = "UPDATE reservation SET fulfilled = ? WHERE reservationid = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReservationDto reservationDto = null;
        try{
            Connection connection = itemReturnOutput.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,itemReturnOutput.getBranchitemid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                preparedStatement = connection.prepareStatement(update);
                preparedStatement.setBoolean(1,true);
                preparedStatement.setInt(2,resultSet.getInt("reservationid"));
                preparedStatement.executeUpdate();

                reservationDto = new ReservationDto(resultSet.getInt("reservationid"),
                        resultSet.getInt("patronid"), resultSet.getInt("branchitemid"),
                        resultSet.getDate("reservdate"), true,
                        resultSet.getInt("forbranchid"));
            }
        } catch (SQLException e) {
            logger.error("SQL error checking reservation for branchitemid {}", itemReturnOutput.getBranchitemid(), e);
            throw new IllegalStateException("SQL error in checking reservation. See log for details");
        } finally {
            connectionUtil.close(preparedStatement);
            connectionUtil.close(resultSet);
        }
        return reservationDto;
    }

}
