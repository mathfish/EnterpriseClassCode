package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemDto;
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

    @Override // example stub to show that there would be potentially multiple implementations of methods for use cases
    public int fulfillReservation(BranchItemDto branchItemDto) {
        return 0;
    }

    @Override
    public int fulfillReservation(BranchItemDao.ReturnItemOutput returnItemOutput){
        String reservationIDQuery = "SELECT reservationid FROM reservation WHERE patronid = ? " +
                "AND branchitemid = ? AND fulfilled = false";
        String reservationUpdate = "Update reservation SET fulfilled = true WHERE reservationid = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        int patronid=0;
        try {
            //Ignore unchecked warning since values already verified existing
            connection = returnItemOutput.getConnection().get();
            preparedStatement = connection.prepareStatement(reservationIDQuery);
            preparedStatement.setInt(1,returnItemOutput.getPatronid().get());
            preparedStatement.setInt(2,returnItemOutput.getBranchitemid().get());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            patronid = resultSet.findColumn("reservationid");

            preparedStatement = connection.prepareStatement(reservationUpdate);
            preparedStatement.setInt(1,patronid);
            preparedStatement.executeUpdate();
            //connection has autocommit to false
            connection.commit();
        } catch (SQLException e1) {
            logger.error("SQL exception occured for patron {}  and branchitem {} \n",returnItemOutput.getPatronid().get(),
                    returnItemOutput.getBranchitemid().get(), e1);
            try {
                connection.rollback();
                throw new IllegalStateException("SQL exception when fulfilling reservation. See log file.");
            } catch (SQLException e) {
                logger.error("SQL exception occured for patron {}  and branchitem {}. Potential rollback failure. \n",returnItemOutput.getPatronid().get(),
                        returnItemOutput.getBranchitemid().get(), e1);
                throw new IllegalStateException("SQL exception when fulfilling reservation. See log file.");
            }

        } finally {
            connectionUtil.close(returnItemOutput.getConnection().get());
            connectionUtil.close(preparedStatement);
            connectionUtil.close(resultSet);
        }
        return patronid;
    }

}
