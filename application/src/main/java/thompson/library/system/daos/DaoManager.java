package thompson.library.system.daos;


public interface DaoManager {
    PatronDao getPatronDao();

    ReservationDao getReservationDao();

    BranchItemDao getBranchItemDao();

    BranchItemCheckoutDao getBranchItemCheckoutDao();

    CheckoutDao getCheckoutDao();
}
