package thompson.library.system.dtos;

import java.sql.Date;

public class CheckoutDto {
    private int checkoutID;
    private int patronid;
    private java.sql.Date checkoutdate;
    private int numberofitems;
    private boolean overdue;
    private boolean itemsreturned;

    public CheckoutDto(int checkoutID, int patronid, Date checkoutdate, int numberofitems, boolean overdue, boolean itemsreturned) {
        this.checkoutID = checkoutID;
        this.patronid = patronid;
        this.checkoutdate = checkoutdate;
        this.numberofitems = numberofitems;
        this.overdue = overdue;
        this.itemsreturned = itemsreturned;
    }


    public int getCheckoutID() {
        return checkoutID;
    }

    public void setCheckoutID(int checkoutID) {
        this.checkoutID = checkoutID;
    }

    public int getPatronid() {
        return patronid;
    }

    public void setPatronid(int patronid) {
        this.patronid = patronid;
    }

    public Date getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(Date checkoutdate) {
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
}
