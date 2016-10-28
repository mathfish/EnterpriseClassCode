package thompson.library.system.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thompson.library.system.daos.*;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;
import thompson.library.system.utilities.LibraryConfig;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
public class BranchServicesInjectTest {

    @Autowired
    BranchServices branchServicesProxy;

    @Test
    public void branchDITest(){
        assertNotNull(branchServicesProxy);
        try{
            //Test DaoManager is injected into BranchServicesImpl using reflection
            Advised advised = (Advised)branchServicesProxy;
            BranchServices branchServices = (BranchServices)advised.getTargetSource().getTarget();

            Field daoManagerField = BranchServicesImpl.class.getDeclaredField("daoManager");
            daoManagerField.setAccessible(true);
            DaoManager daoManager = (DaoManager)daoManagerField.get(branchServices);
            assertNotNull(daoManager);

            // Verify Daos used in BranchServes are injected into DaoManager
            BranchItemCheckoutDao branchItemCheckoutDao = daoManager.getBranchItemCheckoutDao();
            assertNotNull(branchItemCheckoutDao);

            CheckoutDao checkoutDao = daoManager.getCheckoutDao();
            assertNotNull(checkoutDao);

            ReservationDao reservationDao = daoManager.getReservationDao();
            assertNotNull(reservationDao);

            BranchItemDao branchItemDao = daoManager.getBranchItemDao();
            assertNotNull(branchItemDao);

            PatronDao patronDao = daoManager.getPatronDao();
            assertNotNull(patronDao);

            //Verify each Dao has ConnectionFactory and connection util injected
            Field connectionFactoryField = BranchItemCheckoutDaoImpl.class.getDeclaredField("connectionFactory");
            Field connectionUtilField = BranchItemCheckoutDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            ConnectionFactory connectionFactory = (ConnectionFactory) connectionFactoryField.get(branchItemCheckoutDao);
            ConnectionUtil connectionUtil = (ConnectionUtil) connectionUtilField.get(branchItemCheckoutDao);
            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);
            testConnectionAndConnectionUtil(connectionFactory, connectionUtil);

            connectionFactoryField = CheckoutDaoImpl.class.getDeclaredField("connectionFactory");
            connectionUtilField = CheckoutDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            connectionFactory = (ConnectionFactory) connectionFactoryField.get(checkoutDao);
            connectionUtil = (ConnectionUtil) connectionUtilField.get(checkoutDao);
            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);
            testConnectionAndConnectionUtil(connectionFactory, connectionUtil);

            connectionFactoryField = ReservationDaoImpl.class.getDeclaredField("connectionFactory");
            connectionUtilField = ReservationDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            connectionFactory = (ConnectionFactory) connectionFactoryField.get(reservationDao);
            connectionUtil = (ConnectionUtil) connectionUtilField.get(reservationDao);
            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);
            testConnectionAndConnectionUtil(connectionFactory, connectionUtil);

            connectionFactoryField = BranchItemDaoImpl.class.getDeclaredField("connectionFactory");
            connectionUtilField = BranchItemDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            connectionFactory = (ConnectionFactory) connectionFactoryField.get(branchItemDao);
            connectionUtil = (ConnectionUtil) connectionUtilField.get(branchItemDao);
            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);
            testConnectionAndConnectionUtil(connectionFactory, connectionUtil);

            connectionFactoryField = PatronDaoImpl.class.getDeclaredField("connectionFactory");
            connectionUtilField = PatronDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            connectionFactory = (ConnectionFactory) connectionFactoryField.get(patronDao);
            connectionUtil = (ConnectionUtil) connectionUtilField.get(patronDao);
            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);
            testConnectionAndConnectionUtil(connectionFactory, connectionUtil);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            assertFalse(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testConnectionAndConnectionUtil(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil) {
        //Verify SQL connection is valid and connectionUtil works
        Connection connection = connectionFactory.getConnection();
        PreparedStatement query = null;
        ResultSet resultSet = null;
        String selectAll = "SELECT * FROM patron";
        try {
            connection.setAutoCommit(false);
            query = connection.prepareStatement(selectAll);
            resultSet = query.executeQuery();
            assertTrue(resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
            assertFalse(true);
        } finally {
            connectionUtil.close(connection);
            connectionUtil.close(query);
            connectionUtil.close(resultSet);
            try {
                assertTrue(connection.isClosed());
                assertTrue(query.isClosed());
                assertTrue(resultSet.isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
