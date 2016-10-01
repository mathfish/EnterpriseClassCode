package thompson.library.system.daos;

import thompson.library.system.dtos.ReservationDto;

public interface ReservationDao {

    ReservationDto fulfillReservation(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput);
}
