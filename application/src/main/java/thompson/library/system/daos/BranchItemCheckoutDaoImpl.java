package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;

import java.sql.Date;
import java.util.Calendar;


@Repository
public class BranchItemCheckoutDaoImpl implements BranchItemCheckoutDao {

    private static final Logger logger = LoggerFactory.getLogger(BranchItemCheckoutDaoImpl.class);
    private JdbcOperations jdbcOperations;

    @Autowired
    BranchItemCheckoutDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

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

        return new ItemReturnOutput(null, branchItemCheckoutDto.getCheckoutID(),
                branchItemCheckoutDto.getBranchItemID(), false);
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
    }

}
