package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.*;

public class DerbyBranchItemDao implements BranchItemDao {
    private static final Logger logger = LoggerFactory.getLogger(DerbyBranchItemDao.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

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
        return null;
    }

    @Override
    public boolean setIfReserved(BranchItemDto branchItemDto, PatronDto patronDto) {
        return false;
    }

}
