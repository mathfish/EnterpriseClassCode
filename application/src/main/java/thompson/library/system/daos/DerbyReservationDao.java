package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.ReservationDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DerbyReservationDao implements ReservationDao {
    private static final Logger logger = LoggerFactory.getLogger(DerbyReservationDao.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    DerbyReservationDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }


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
