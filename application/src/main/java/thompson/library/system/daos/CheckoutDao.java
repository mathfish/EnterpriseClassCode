package thompson.library.system.daos;

import thompson.library.system.dtos.CheckoutDto;

public interface CheckoutDao {

    void updateCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    CheckoutDto getCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);
}
