package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.*;
import java.util.Calendar;


@Repository
public class BranchItemCheckoutDaoImpl implements BranchItemCheckoutDao {

    private static final Logger logger = LoggerFactory.getLogger(BranchItemCheckoutDaoImpl.class);
    private JdbcOperations jdbcOperations;
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;
    private Connection connection;

    @Autowired
    BranchItemCheckoutDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }
//    BranchItemCheckoutDaoImpl(
//            @Value("#{T(thompson.library.system.utilities.ConnectionManager).getConnectionFactory()}")
//                    ConnectionFactory connectionFactory,
//            ConnectionUtil connectionUtil){
//        this.connectionFactory = connectionFactory;
//        this.connectionUtil = connectionUtil;
//    }

    /**
     *
     * Returns information on the branch item for a checkout. Returns branchItemDto
     */
    @Transactional
    @Override
    public BranchItemCheckoutDto getBranchItemCheckout(BranchItemDto branchItemDto) {
        String query = "SELECT * FROM branchitemcheckout WHERE branchitemid = ? " +
                "AND returned = false";
        return jdbcOperations.queryForObject(query, ((rs, rowNum) -> {
            return new BranchItemCheckoutDto(rs.getInt("checkoutid"),
                    rs.getInt("branchitemid"), rs.getBoolean("overdue"),
                    rs.getDate("duedate"),rs.getBoolean("renew"), rs.getDate("renewDate"),
                    rs.getBoolean("returned"), rs.getDate("returndate"));
        }), branchItemDto.getBranchitemid());
//        connection = connectionFactory.getConnection();
//        String query = "SELECT * FROM branchitemcheckout WHERE branchitemid = ? " +
//                "AND returned = false";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        BranchItemCheckoutDto brItemCheckout = null;
//        try{
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1,branchItemDto.getBranchitemid());
//            resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                brItemCheckout = new BranchItemCheckoutDto(resultSet.getInt("checkoutid"),
//                        resultSet.getInt("branchitemid"), resultSet.getBoolean("overdue"),
//                        resultSet.getDate("duedate"), resultSet.getBoolean("renew"), resultSet.getDate("renewDate"),
//                        resultSet.getBoolean("returned"), resultSet.getDate("returndate"));
//            }
//        } catch (SQLException e) {
//            logger.error("SQL exception getting branchItemCheckout object. branchitemid: {} ",
//                    branchItemDto.getBranchitemid(), e);
//            throw new IllegalStateException("SQL exception when getting branchitemcheckout. See log for details");
//        } finally {
//            connectionUtil.close(connection);
//            connectionUtil.close(preparedStatement);
//            connectionUtil.close(resultSet);
//        }
//
//        return brItemCheckout;
    }

    /**
     *
     * Updates a branch item checkout using transfer object branchItemCheckoutDto
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public BranchItemCheckoutDao.ItemReturnOutput updateBranchItemCheckout(BranchItemCheckoutDto branchItemCheckoutDto) {
        String update = "UPDATE branchitemcheckout SET returned = ?, returnDate = ?, overdue = ? " +
                "WHERE branchitemid = ? AND checkoutid = ?";
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTime().getTime());

        jdbcOperations.update(update,true,
                                     date,
                                     branchItemCheckoutDto.isOverdue(),
                                     branchItemCheckoutDto.getBranchItemID(),
                                     branchItemCheckoutDto.getCheckoutID());

        return new ItemReturnOutput(connection, branchItemCheckoutDto.getCheckoutID(),
                branchItemCheckoutDto.getBranchItemID(), false);

//        connection = connectionFactory.getConnection();
//
//        String updateBranchItemCheckout = "UPDATE branchitemcheckout SET returned = ?, returnDate = ?, overdue = ? " +
//                "WHERE branchitemid = ? AND checkoutid = ?";
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date(calendar.getTime().getTime());
//        PreparedStatement preparedStatement = null;
//        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput;
//        try{
//            connection.setAutoCommit(false);
//            preparedStatement = connection.prepareStatement(updateBranchItemCheckout);
//            preparedStatement.setBoolean(1,true);
//            preparedStatement.setDate(2,date);
//            preparedStatement.setBoolean(3, branchItemCheckoutDto.isOverdue());
//
//            preparedStatement.setInt(4, branchItemCheckoutDto.getBranchItemID());
//            preparedStatement.setInt(5, branchItemCheckoutDto.getCheckoutID());
//            preparedStatement.executeUpdate();
//            itemReturnOutput = new ItemReturnOutput(connection, branchItemCheckoutDto.getCheckoutID(),
//                    branchItemCheckoutDto.getBranchItemID(), false);
//        } catch (SQLException e) {
//            logger.error("SQL error in updating branchItemCheckout. checkoutid {}, branchitemid {}",
//                    branchItemCheckoutDto.getCheckoutID(), branchItemCheckoutDto.getBranchItemID(), e);
//            throw new IllegalStateException("SQL exception updateing branchCheckoutItem. See log for details");
//        } finally {
//            connectionUtil.close(preparedStatement);
//        }
//
//        return itemReturnOutput;
    }

    /**
     *
     * Returns the total number of items that have been returned from a checkout. That is a checkout can have multiple
     * items, each of which of can be returned at different times.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int getNumberOfItemsReturnedFromCheckout(ItemReturnOutput itemReturnOutput) {
        String query = "SELECT COUNT(*) FROM branchitemcheckout WHERE checkoutid = ? AND returned = true";
        return jdbcOperations.queryForObject(query, new Object[]{itemReturnOutput.getCheckoutid()}, Integer.class);

//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        int total = 0;
//        try{
//            Connection connection = itemReturnOutput.getConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1,itemReturnOutput.getCheckoutid());
//            resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                total = resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            logger.error("SQL error in calculating total returned items for checkout id {}", itemReturnOutput.getCheckoutid(), e);
//            throw new IllegalStateException("SQL error in calculating number of returned items. See log for details");
//        } finally {
//            connectionUtil.close(preparedStatement);
//            connectionUtil.close(resultSet);
//        }
//        return total;
    }

}
