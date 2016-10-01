package thompson.library.system.dtos;

import java.sql.Date;

public class BranchItemCheckoutDto {
    private Integer checkoutID;
    private Integer branchItemID;
    private boolean overdue;
    private java.sql.Date dueDate;
    private boolean renew;
    private java.sql.Date renewDate;
    private boolean returned;
    private java.sql.Date returnDate;

    public BranchItemCheckoutDto(Integer checkoutID, Integer branchItemID, boolean overdue,
                                 Date dueDate, boolean renew, Date renewDate, boolean returned, Date returnDate) {
        this.checkoutID = checkoutID;
        this.branchItemID = branchItemID;
        this.overdue = overdue;
        this.dueDate = dueDate;
        this.renew = renew;
        this.renewDate = renewDate;
        this.returned = returned;
        this.returnDate = returnDate;
    }

    public Integer getCheckoutID() {
        return checkoutID;
    }

    public Integer getBranchItemID() {
        return branchItemID;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isRenew() {
        return renew;
    }

    public Date getRenewDate() {
        return renewDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setCheckoutID(Integer checkoutID) {
        this.checkoutID = checkoutID;
    }

    public void setBranchItemID(Integer branchItemID) {
        this.branchItemID = branchItemID;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
