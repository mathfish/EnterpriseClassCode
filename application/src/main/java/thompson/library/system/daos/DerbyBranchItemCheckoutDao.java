package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

/**
 * Created by jonathanthompson on 9/29/16.
 */
public class DerbyBranchItemCheckoutDao implements BranchItemCheckoutDao {

    ConnectionFactory connectionFactory;
    ConnectionUtil connectionUtil;
    DerbyBranchItemCheckoutDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }


}
