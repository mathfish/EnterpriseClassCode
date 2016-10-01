package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.*;
import java.util.Calendar;

public class DerbyBranchItemDao implements BranchItemDao {
    private static final Logger logger = LoggerFactory.getLogger(DerbyBranchItemDao.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;
    private Connection connection;

    DerbyBranchItemDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    @Override
    public void updateBranchItem(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String update = "UPDATE branchitem SET reserved = ?, checkedout = false WHERE branchitemid = ?";
        PreparedStatement preparedStatement = null;
        try{
            //Optionals exist from prior query
            Connection connection = itemReturnOutput.getConnection();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setBoolean(1,itemReturnOutput.isReserved());
            preparedStatement.setInt(2,itemReturnOutput.getBranchitemid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL error in updating branch item {}", itemReturnOutput.getBranchitemid(), e);
            throw new IllegalStateException("SQL error in updating branch item. See log for details");
        } finally {
            connectionUtil.close(preparedStatement);
        }
    }

    @Override
    public ReturnItemOutput returnItem(BranchItemDto branchItemDto, PatronDto patronDto) {
        connection = connectionFactory.getConnection();
        String updateBranchItemCheckout = "UPDATE branchitemcheckout SET returned = ?, returnDate = ? WHERE branchitemid = ? AND returned = false";
        String updateBranchItem = "UPDATE branchitem SET checkedout = false WHERE branchitemid = ?";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTime().getTime());
            preparedStatement = connection.prepareStatement(updateBranchItemCheckout);
            preparedStatement.setBoolean(1,true);
            preparedStatement.setDate(2,date);
            preparedStatement.setInt(3, branchItemDto.getBranchitemid());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(updateBranchItem);
            preparedStatement.setInt(1, branchItemDto.getBranchitemid());
            preparedStatement.executeUpdate();

            if(setIfReserved(branchItemDto,  patronDto)){
                return new ReturnItemOutput(connection, patronDto.getPatronid(), branchItemDto.getBranchitemid());
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred returning item {} for patron {} \n.", branchItemDto.getBranchitemid(),
                    patronDto.getPatronid().get(), e);
            try {
                connection.rollback();
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                throw new IllegalStateException("SQL exception occured. See log file for details");
            } catch (SQLException e1) {
                logger.error("SQL exception occurred returning item {} for patron {}. Potential rollback failure \n.",
                        branchItemDto.getBranchitemid(), patronDto.getPatronid().get(), e);
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                throw new IllegalStateException("SQL exception occurred. See log file for details");
            }
        } finally {
            connectionUtil.close(preparedStatement);
        }
        return new ReturnItemOutput(connection);
    }

    @Override
    public boolean setIfReserved(BranchItemDto branchItemDto, PatronDto patronDto){
        String isreservedQuery = "SELECT patronid, reservationid FROM reservation WHERE branchitemid = ? AND fulfilled = false ORDER BY reservdate";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean externalCall = false;
        //if being called from ReturnItemOutput, already has connection
        try {
            if(connection.isClosed() || connection == null){
                connection = connectionFactory.getConnection();
                externalCall = true;
            }
            preparedStatement = connection.prepareStatement(isreservedQuery);
            preparedStatement.setInt(1, branchItemDto.getBranchitemid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String setReserve = "UPDATE branchitem SET reserved = true WHERE branchitemid = ? ";
                preparedStatement = connection.prepareStatement(setReserve);
                preparedStatement.setInt(1, branchItemDto.getBranchitemid());
                preparedStatement.executeUpdate();
                connectionUtil.close(resultSet);
                connectionUtil.close(preparedStatement);
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred when checking reservation of item {} for patron {} \n",
                    branchItemDto.getBranchitemid(), patronDto.getPatronid().get(), e );
            try {
                connection.rollback();
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                connectionUtil.close(resultSet);
                throw new IllegalStateException("SQL exception occured. See log file for more information");
            } catch (SQLException e1) {
                logger.error("SQL exception occurred when checking reservation of item {} for patron {}." +
                        "Potential rollback failure \n",
                        branchItemDto.getBranchitemid(), patronDto.getPatronid().get(), e );
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                connectionUtil.close(resultSet);
                throw new IllegalStateException("SQL exception occured. See log file for more information");
            }
        }
        if(externalCall) {
            connectionUtil.close(connection);
        }
        connectionUtil.close(preparedStatement);
        connectionUtil.close(resultSet);
        return false;
    }
}
