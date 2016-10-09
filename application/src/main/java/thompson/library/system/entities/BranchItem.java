package thompson.library.system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
public class BranchItem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branchitemid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchItemid")
    private Set<Reservation> reservations;

    @JoinColumn(name = "branchid")
    @ManyToOne
    private Branch branchid;

    @JoinColumn(name = "currentlocation")
    @ManyToOne
    private Branch currentlocation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchitemid")
    private Set<BranchItemCheckout> branchItemCheckouts;

    private boolean checkedout;

    private boolean reserved;

    private boolean intransit;

    public int getBranchitemid() {
        return branchitemid;
    }

    public void setBranchitemid(int branchitemid) {
        this.branchitemid = branchitemid;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Branch getBranchid() {
        return branchid;
    }

    public void setBranchid(Branch branchid) {
        this.branchid = branchid;
    }

    public Branch getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(Branch currentlocation) {
        this.currentlocation = currentlocation;
    }

    public Set<BranchItemCheckout> getBranchItemCheckouts() {
        return branchItemCheckouts;
    }

    public void setBranchItemCheckouts(Set<BranchItemCheckout> branchItemCheckouts) {
        this.branchItemCheckouts = branchItemCheckouts;
    }

    public boolean isCheckedout() {
        return checkedout;
    }

    public void setCheckedout(boolean checkedout) {
        this.checkedout = checkedout;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isIntransit() {
        return intransit;
    }

    public void setIntransit(boolean intransit) {
        this.intransit = intransit;
    }
}
