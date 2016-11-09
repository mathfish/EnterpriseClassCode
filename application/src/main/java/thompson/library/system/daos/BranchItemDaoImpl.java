package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BranchItemDaoImpl implements BranchItemDao {

    private static final Logger logger = LoggerFactory.getLogger(BranchItemDaoImpl.class);
    private JdbcOperations jdbcOperations;

    @Autowired
    BranchItemDaoImpl(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }
    /**
     *
     * Updates the state of the branch item using the itemReturnOutput. Part of multiple steps of the return process
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void updateBranchItem(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        String update = "UPDATE branchitem SET reserved = ?, checkedout = false WHERE branchitemid = ?";
        jdbcOperations.update(update, itemReturnOutput.isReserved(), itemReturnOutput.getBranchitemid());
    }
}
