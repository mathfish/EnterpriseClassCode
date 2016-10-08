package thompson.library.system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class BranchItemCheckout implements Serializable{

    @EmbeddedId
    private BranchItemCheckoutId id;

    @ManyToOne(cascade = CascadeType.ALL)
    private BranchItem branchitemid;

    @ManyToOne(cascade = CascadeType.ALL)
    private Checkout checkoutid;

    private boolean overdue;

    private java.sql.Date duedate;

    private boolean renew;

    private java.sql.Date renewdate;

    private boolean returned;

    private java.sql.Date returndate;

    public BranchItemCheckoutId getId() {
        return id;
    }

    public void setId(BranchItemCheckoutId id) {
        this.id = id;
    }

    public BranchItem getBranchitemid() {
        return branchitemid;
    }

    public void setBranchitemid(BranchItem branchitemid) {
        this.branchitemid = branchitemid;
    }

    public Checkout getCheckoutid() {
        return checkoutid;
    }

    public void setCheckoutid(Checkout checkoutid) {
        this.checkoutid = checkoutid;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public boolean isRenew() {
        return renew;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    public Date getRenewdate() {
        return renewdate;
    }

    public void setRenewdate(Date renewdate) {
        this.renewdate = renewdate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }
}
