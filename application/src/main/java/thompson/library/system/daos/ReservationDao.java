package thompson.library.system.daos;

import thompson.library.system.dtos.ItemDto;

import java.sql.SQLException;

public interface ReservationDao {

    int fulfillReservation(ItemDto itemDto);

    int fulfillReservation(BranchItemDao.ReturnItemOutput returnItemOutput);
}
