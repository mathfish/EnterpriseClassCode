package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.entities.Checkout;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutDaoImpl implements CheckoutDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;
    private SessionFactory sessionFactory;

    CheckoutDaoImpl(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    CheckoutDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Updates a checkout with the itemReturnOutput object. Part of a many step process for returning an item.
     */
    @Override
    public void updateCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        Checkout checkout =
        (Checkout)currentSession.createQuery("SELECT ch FROM Checkout ch WHERE ch.checkoutid = :id")
                .setParameter("id", itemReturnOutput.getCheckoutid())
                .getSingleResult();

        checkout.setItemsreturned(itemReturnOutput.isReturned());
        currentSession.saveOrUpdate(checkout);
        if(commitTrans){
            currentSession.getTransaction().commit();
        }
    }

    /**
     *
     * Returns a checkout row using the information in the itemReturnOutput object.
     * Part of a many step process for returning an item.
     */
    @Override
    public CheckoutDto getCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        Checkout checkout =
                (Checkout)currentSession.createQuery("SELECT ch FROM Checkout ch WHERE ch.checkoutid = :id")
                        .setParameter("id", itemReturnOutput.getCheckoutid())
                        .getSingleResult();
        if(commitTrans){
            currentSession.getTransaction().commit();
        }
        return new CheckoutDto(checkout.getCheckoutid(), checkout.getPatronid().getPatronid(),
                checkout.getCheckoutdate(), checkout.getNumberofitems(), checkout.isOverdue(),
                checkout.isItemsreturned());
//
//
//
//
//
//        String query = "SELECT * FROM checkout WHERE checkoutid = ?";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        CheckoutDto checkoutDto = null;
//        try{
//            Connection connection = itemReturnOutput.getConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1,itemReturnOutput.getCheckoutid());
//            resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                checkoutDto = new CheckoutDto(resultSet.getInt("checkoutid"),
//                        resultSet.getInt("patronid"), resultSet.getTimestamp("checkoutdate"),
//                        resultSet.getInt("numberofitems"),resultSet.getBoolean("overdue"),
//                        resultSet.getBoolean("itemsreturned"));
//            }
//        } catch (SQLException e) {
//           logger.error("SQL error getting checkout with id {}", itemReturnOutput.getCheckoutid(), e);
//            throw new IllegalStateException("SQL error in getting checkout. See log for details");
//        } finally {
//            connectionUtil.close(preparedStatement);
//            connectionUtil.close(resultSet);
//        }
//        return checkoutDto;
    }
}
