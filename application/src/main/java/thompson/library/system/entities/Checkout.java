package thompson.library.system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Checkout implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkoutid;

    @ManyToOne(cascade=CascadeType.ALL)
    private Patron patronid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkoutid")
    private Set<BranchItemCheckout> branchItemCheckouts;

    private java.sql.Timestamp checkoutdate;
    private int numberofitems;
    private boolean overdue;
    private boolean itemsreturned;

    public Set<BranchItemCheckout> getBranchItemCheckouts() {
        return branchItemCheckouts;
    }

    public void setBranchItemCheckouts(Set<BranchItemCheckout> branchItemCheckouts) {
        this.branchItemCheckouts = branchItemCheckouts;
    }

    public int getCheckoutid() {
        return checkoutid;
    }

    public void setCheckoutid(int checkoutid) {
        this.checkoutid = checkoutid;
    }

    public Timestamp getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(Timestamp checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    public int getNumberofitems() {
        return numberofitems;
    }

    public void setNumberofitems(int numberofitems) {
        this.numberofitems = numberofitems;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isItemsreturned() {
        return itemsreturned;
    }

    public void setItemsreturned(boolean itemsreturned) {
        this.itemsreturned = itemsreturned;
    }

    public Patron getPatronid() {
        return patronid;
    }

    public void setPatronid(Patron patronid) {
        this.patronid = patronid;
    }
}
