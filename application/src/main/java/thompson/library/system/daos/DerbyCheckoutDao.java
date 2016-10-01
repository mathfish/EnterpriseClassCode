package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DerbyCheckoutDao implements CheckoutDao {
    private static final Logger logger = LoggerFactory.getLogger(DerbyReservationDao.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    DerbyCheckoutDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }



    @Override
    public void updateCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String update = "UPDATE checkout SET itemsreturned = ? WHERE checkoutid = ?";
        PreparedStatement preparedStatement = null;
        try{
            Connection connection = itemReturnOutput.getConnection();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setBoolean(1,itemReturnOutput.isReturned());
            preparedStatement.setInt(2,itemReturnOutput.getCheckoutid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL error updating checkout with id {}", itemReturnOutput.getCheckoutid(), e);
            throw new IllegalStateException("SQL error in updating checkout. See log for details");
        } finally {
            connectionUtil.close(preparedStatement);
        }

    }

    @Override
    public CheckoutDto getCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String query = "SELECT * FROM checkout WHERE checkoutid = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CheckoutDto checkoutDto = null;
        try{
            Connection connection = itemReturnOutput.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,itemReturnOutput.getCheckoutid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                checkoutDto = new CheckoutDto(resultSet.getInt("checkoutid"),
                        resultSet.getInt("patronid"), resultSet.getTimestamp("checkoutdate"),
                        resultSet.getInt("numberofitems"),resultSet.getBoolean("overdue"),
                        resultSet.getBoolean("itemsreturned"));
            }
        } catch (SQLException e) {
           logger.error("SQL error getting checkout with id {}", itemReturnOutput.getCheckoutid(), e);
            throw new IllegalStateException("SQL error in getting checkout. See log for details");
        } finally {
            connectionUtil.close(preparedStatement);
            connectionUtil.close(resultSet);
        }
        return checkoutDto;
    }
}
