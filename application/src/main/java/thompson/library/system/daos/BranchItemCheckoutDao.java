package thompson.library.system.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;

import java.sql.Connection;
import java.sql.SQLException;


public interface BranchItemCheckoutDao {

    BranchItemCheckoutDto getBranchItemCheckout(BranchItemDto branchItemDto);

    ItemReturnOutput updateBranchItemCheckout(BranchItemCheckoutDto branchItemCheckoutDto);

    int getNumberOfItemsReturnedFromCheckout(ItemReturnOutput itemReturnOutput);

    /**
     * Object used to connection the multiple parts of return. Allows for hiding of the database particulars but maintains
     * a single transaction among the steps.
     */
    class ItemReturnOutput{
        private Connection connection;
        private boolean reserved;
        private boolean returned;
        private Integer checkoutid;
        private Integer branchitemid;
        private Integer patronid;

        private static final Logger logger = LoggerFactory.getLogger(BranchItemCheckoutDao.ItemReturnOutput.class);

        public ItemReturnOutput(Connection connection, Integer patronid, Integer branchitemid, boolean reserved) {
            this.connection = connection;
            this.reserved = reserved;
            this.patronid = patronid;
            this.branchitemid = branchitemid;
        }

        public void completeReturn(){
            if(connection != null){
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    logger.error("SQL exception during commit and rollback for patronid {} and branchitemid {}",
                            checkoutid, branchitemid, e);
                    throw new IllegalStateException("SQL error during commit and rollback. See log for more details");
                }
            }
        }

        public Connection getConnection() {
            return connection;
        }

        public boolean isReserved() {
            return reserved;
        }

        public Integer getCheckoutid() {
            return checkoutid;
        }

        public Integer getBranchitemid() {
            return branchitemid;
        }

        public Integer getPatronid() {
            return patronid;
        }

        public void setPatronid(Integer patronid) {
            this.patronid = patronid;
        }

        public void setReserved(boolean reserved) {
            this.reserved = reserved;
        }

        public boolean isReturned() {
            return returned;
        }

        public void setReturned(boolean returned) {
            this.returned = returned;
        }
    }

}
