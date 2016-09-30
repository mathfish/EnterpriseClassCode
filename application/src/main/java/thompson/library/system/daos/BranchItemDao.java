package thompson.library.system.daos;

import thompson.library.system.dtos.ItemDto;
import thompson.library.system.dtos.PatronDto;

import java.sql.Connection;
import java.util.Optional;

public interface BranchItemDao {

    ReturnItemOutput returnItem(ItemDto itemDto, PatronDto patronDto);

    boolean setIfReserved(ItemDto itemDto, PatronDto patronDto);

    class ReturnItemOutput{
        Optional<Connection> connection;
        boolean fulfillReturn;
        Optional<Integer> patronid;
        Optional<Integer> branchitemid;

        public ReturnItemOutput(Connection connection, Optional<Integer> patronid, int branchitemid) {
            this.fulfillReturn = true;
            this.connection = Optional.of(connection);
            this.patronid = patronid;
            this.branchitemid = Optional.of(branchitemid);
        }


        public ReturnItemOutput() {
            this.patronid = Optional.empty();
            this.branchitemid = Optional.empty();
            this.connection = Optional.empty();
            this.fulfillReturn = false;
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
