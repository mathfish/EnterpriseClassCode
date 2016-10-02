package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface BranchItemDao {

    void updateBranchItem(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    /**
     * Object used to connection the multiple parts of return. Allows for hiding of the database particulars but maintains
     * a single transaction among the steps.
     */
    class ReturnItemOutput{
        Optional<Connection> connection;
        boolean fulfillReturn;
        Optional<Integer> patronid;
        Optional<Integer> branchitemid;

        private static final Logger logger = LoggerFactory.getLogger(ReturnItemOutput.class);

        public ReturnItemOutput(Connection connection, Optional<Integer> patronid, int branchitemid) {
            this.fulfillReturn = true;
            this.connection = Optional.of(connection);
            this.patronid = patronid;
            this.branchitemid = Optional.of(branchitemid);
        }

        public ReturnItemOutput(Connection connection) {
            this.patronid = Optional.empty();
            this.branchitemid = Optional.empty();
            this.connection = Optional.of(connection);
            this.fulfillReturn = false;
        }

        public void completeReturn(){
            if(connection.isPresent()){
                try {
                    connection.get().commit();
                    connection.get().close();
                } catch (SQLException e) {
                    logger.error("SQL exception during commit and rollback for patronid {} and branchitemid {}",
                            patronid.get(), branchitemid.get(), e);
                    throw new IllegalStateException("SQL error during commit and rollback. See log for more details");
                }
            }
        }

        public Optional<Connection> getConnection() {
            return connection;
        }

        public boolean isFulfillReturn() {
            return fulfillReturn;
        }

        public Optional<Integer> getPatronid() {
            return patronid;
        }

        public Optional<Integer> getBranchitemid() {
            return branchitemid;
        }
    }

}
