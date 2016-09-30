package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.ItemDto;
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
    public ReturnItemOutput returnItem(ItemDto itemDto, PatronDto patronDto) {
        connection = connectionFactory.getConnection();
        String updateBranchItemCheckout = "UPDATE branchitemcheckout SET returned = ?, returnDate = ? WHERE branchitemid = ? AND returned false";
        String updateBranchItem = "UPDATE branchitem SET checkedout = false WHERE branchitem = ?";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTime().getTime());
            preparedStatement = connection.prepareStatement(updateBranchItemCheckout);
            preparedStatement.setBoolean(1,true);
            preparedStatement.setDate(2,date);
            preparedStatement.setInt(3,itemDto.getBranchitemid());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(updateBranchItem);
            preparedStatement.setInt(1,itemDto.getBranchitemid());
            preparedStatement.executeUpdate();

            if(setIfReserved(itemDto,  patronDto)){
                return new ReturnItemOutput(connection, patronDto.getPatronid(), itemDto.getBranchitemid());
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("SQL exception occurred returning item {} for patron {} \n.", itemDto.getBranchitemid(),
                    patronDto.getPatronid().get(), e);
            try {
                connection.rollback();
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                throw new IllegalStateException("SQL exception occured. See log file for details");
            } catch (SQLException e1) {
                logger.error("SQL exception occurred returning item {} for patron {}. Potential rollback failure \n.",
                        itemDto.getBranchitemid(), patronDto.getPatronid().get(), e);
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                throw new IllegalStateException("SQL exception occurred. See log file for details");
            }
        }

        connectionUtil.close(connection);
        connectionUtil.close(preparedStatement);
        return new ReturnItemOutput();
    }

    @Override
    public boolean setIfReserved(ItemDto itemDto, PatronDto patronDto){
        String isreservedQuery = "SELECT patronid, reservationid FROM reservation WHERE branchitem = ? AND fulfilled = false ORDER BY date";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //if being called from ReturnItemOutput, already has connection
        try {
            if(connection.isClosed() || connection == null){
                connection = connectionFactory.getConnection();
            }
            preparedStatement = connection.prepareStatement(isreservedQuery);
            preparedStatement.setInt(1,itemDto.getBranchitemid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String setReserve = "UPDATE branchitem SET reserved = true WHERE branchitemid = ? ";
                preparedStatement = connection.prepareStatement(setReserve);
                preparedStatement.setInt(1,itemDto.getBranchitemid());
                preparedStatement.executeUpdate();
                connectionUtil.close(resultSet);
                connectionUtil.close(preparedStatement);
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred when checking reservation of item {} for patron {} \n",
                    itemDto.getBranchitemid(), patronDto.getPatronid().get(), e );
            try {
                connection.rollback();
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                connectionUtil.close(resultSet);
                throw new IllegalStateException("SQL exception occured. See log file for more information");
            } catch (SQLException e1) {
                logger.error("SQL exception occurred when checking reservation of item {} for patron {}." +
                        "Potential rollback failure \n",
                        itemDto.getBranchitemid(), patronDto.getPatronid().get(), e );
                connectionUtil.close(connection);
                connectionUtil.close(preparedStatement);
                connectionUtil.close(resultSet);
                throw new IllegalStateException("SQL exception occured. See log file for more information");
            }
        }
        connectionUtil.close(connection);
        connectionUtil.close(preparedStatement);
        connectionUtil.close(resultSet);
        return false;
    }
}
