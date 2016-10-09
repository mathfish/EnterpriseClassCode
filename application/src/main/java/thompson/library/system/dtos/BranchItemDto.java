package thompson.library.system.dtos;

import java.util.Optional;

public class BranchItemDto {
    private final int branchitemid;
    private final boolean checkedout;
    private final boolean reserved;
    private final boolean inTransit;
    private final Optional<Integer> currentLocation;
    private final int branchid;

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
