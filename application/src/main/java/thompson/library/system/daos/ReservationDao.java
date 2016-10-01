package thompson.library.system.daos;

import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.ReservationDto;

public interface ReservationDao {

    ReservationDto fulfillReservation(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);

    int fulfillReservation(BranchItemDto branchItemDto);

    int fulfillReservation(BranchItemDao.ReturnItemOutput returnItemOutput);
}
