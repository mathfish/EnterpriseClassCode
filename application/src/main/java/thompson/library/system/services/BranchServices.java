package thompson.library.system.services;

import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronD;

public interface BranchServices {

    void returnItem(BranchItemDto branchItemDto);

    void emailPatron(PatronD patronD, String message);
}
