package thompson.library.system.services;

import thompson.library.system.dtos.ItemDto;
import thompson.library.system.dtos.PatronDto;

public interface BranchServices {

    void returnItem(ItemDto itemDto, PatronDto patronDto);

    void emailPatron(PatronDto patronDto, String message);
}
