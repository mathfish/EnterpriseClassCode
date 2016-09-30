package thompson.library.system.daos;

import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

public class DerbyItemDao implements ItemDao {

    private ConnectionFactory connectionFactory;
    private ConnectionUtil connectionUtil;

    public DerbyItemDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil) {
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }
}
