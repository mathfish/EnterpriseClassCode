package thompson.library.system.daos;

import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.utilities.ConnectionFactory;
import thompson.library.system.utilities.ConnectionUtil;

public class DerbyCheckoutDao implements CheckoutDao {

    ConnectionFactory connectionFactory;
    ConnectionUtil connectionUtil;
    DerbyCheckoutDao(ConnectionFactory connectionFactory, ConnectionUtil connectionUtil){
        this.connectionFactory = connectionFactory;
        this.connectionUtil = connectionUtil;
    }

    @Override
    public CheckoutDto getCheckout() {
        return null;
    }
}
