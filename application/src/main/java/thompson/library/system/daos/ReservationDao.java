package thompson.library.system.daos;

import thompson.library.system.dtos.BranchItemDto;

public interface ReservationDao {

    int fulfillReservation(BranchItemDto branchItemDto);

    int fulfillReservation(BranchItemDao.ReturnItemOutput returnItemOutput);
}
