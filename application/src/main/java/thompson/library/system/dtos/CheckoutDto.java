package thompson.library.system.dtos;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CheckoutDto {
    private int checkoutID;
    private int patronid;
    private java.sql.Timestamp checkoutdate;
    private int numberofitems;
    private boolean overdue;
    private boolean itemsreturned;

    CheckoutDto(){}

    public CheckoutDto(int checkoutID, int patronid, Timestamp checkoutdate, int numberofitems, boolean overdue, boolean itemsreturned) {
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
}
