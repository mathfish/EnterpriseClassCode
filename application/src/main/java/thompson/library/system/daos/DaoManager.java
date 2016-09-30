package thompson.library.system.daos;

import thompson.library.system.dtos.PatronDto;

public interface DaoManager {
    PatronDao getPatronDao();

    ItemDao getItemDao();

    ReservationDao getReservationDao();

    BranchItemDao getBranchItemDao();

    CheckoutDao getCheckoutDao();
}
