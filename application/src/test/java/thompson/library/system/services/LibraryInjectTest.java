package thompson.library.system.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thompson.library.system.daos.DaoManager;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.daos.PatronDaoImpl;
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
public class LibraryInjectTest {

    @Autowired
    LibraryServices libraryServices;

    @Test
    public void libraryDITest(){
        //verify Library Services is injected
        assertNotNull(libraryServices);
        try {
            //Get Dao Manager by reflection
            Field daoManagerField = LibraryServicesImpl.class.getDeclaredField("daoManager");
            daoManagerField.setAccessible(true);
            DaoManager daoManager = (DaoManager) daoManagerField.get(libraryServices);
            //Verify DaoManager is injected into Library Services
            assertNotNull(daoManager);

            //Verify DaoManager has PatronDao injected
            PatronDao patronDao = daoManager.getPatronDao();
            assertNotNull(patronDao);

            //Verify PatronDao has ConnectionFactory and ConnectionUtil injected
            Field connectionFactoryField = PatronDaoImpl.class.getDeclaredField("connectionFactory");
            Field connectionUtilField = PatronDaoImpl.class.getDeclaredField("connectionUtil");
            connectionFactoryField.setAccessible(true);
            connectionUtilField.setAccessible(true);
            ConnectionFactory connectionFactory = (ConnectionFactory) connectionFactoryField.get(patronDao);
            ConnectionUtil connectionUtil = (ConnectionUtil) connectionUtilField.get(patronDao);

            assertNotNull(connectionFactory);
            assertNotNull(connectionUtil);

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
            }  finally {
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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            assertFalse(true);
        }

    }
}
