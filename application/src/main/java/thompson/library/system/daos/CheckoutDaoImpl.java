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
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CheckoutDaoImpl implements CheckoutDao {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutDaoImpl.class);
    private JdbcOperations jdbcOperations;

    @Autowired
    CheckoutDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    /**
     *
     * Updates a checkout with the itemReturnOutput object. Part of a many step process for returning an item.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void updateCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String update = "UPDATE checkout SET itemsreturned = ? WHERE checkoutid = ?";
        jdbcOperations.update(update, itemReturnOutput.isReturned(), itemReturnOutput.getCheckoutid());
    }

    /**
     *
     * Returns a checkout row using the information in the itemReturnOutput object.
     * Part of a many step process for returning an item.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public CheckoutDto getCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String query = "SELECT * FROM checkout WHERE checkoutid = ?";
        return jdbcOperations.queryForObject(query, (rs,rowNum) ->{
            return new CheckoutDto(rs.getInt("checkoutid"),
                                    rs.getInt("patronid"),
                                    rs.getTimestamp("checkoutdate"),
                                    rs.getInt("numberofitems"),
                                    rs.getBoolean("overdue"),
                                    rs.getBoolean("itemsreturned"));
        }, itemReturnOutput.getCheckoutid());
    }
}
