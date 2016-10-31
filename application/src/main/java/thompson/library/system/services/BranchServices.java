package thompson.library.system.services;

import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.Dto;

public interface BranchServices {

    void returnItem(BranchItemDto branchItemDto);

    void emailPatron(Dto dto, String message);
}
