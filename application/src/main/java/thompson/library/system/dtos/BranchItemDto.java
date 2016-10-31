package thompson.library.system.dtos;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BranchItemDto {
    private int branchitemid;
    private boolean checkedout;
    private boolean reserved;
    private boolean inTransit;
    private Optional<Integer> currentLocation;
    private int branchid;

    BranchItemDto(){}

    public BranchItemDto(int branchitemid, boolean checkedout, boolean reserved, boolean inTransit, int currentLocation, int branchid) {
        this.branchitemid = branchitemid;
        this.checkedout = checkedout;
        this.reserved = reserved;
        this.inTransit = inTransit;
        this.currentLocation = Optional.of(currentLocation);
        this.branchid = branchid;
    }

    public BranchItemDto(int branchitemid, boolean checkedout, boolean reserved, boolean inTransit, int branchid) {
        this.branchitemid = branchitemid;
        this.checkedout = checkedout;
        this.reserved = reserved;
        this.inTransit = inTransit;
        this.currentLocation = Optional.empty();
        this.branchid = branchid;
    }

    public int getBranchitemid() {
        return branchitemid;
    }

    public boolean isCheckedout() {
        return checkedout;
    }

    public boolean isReserved() {
        return reserved;
    }

    public boolean isInTransit() {
        return inTransit;
    }

    public Optional<Integer> getCurrentLocation() {
        return currentLocation;
    }

    public int getBranchid() {
        return branchid;
    }
}
