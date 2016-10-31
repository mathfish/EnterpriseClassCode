package thompson.library.system.services;

import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;

public interface BranchServices {

    void returnItem(BranchItemDto branchItemDto);

    void emailPatron(PatronDto patronDto, String message);
}
