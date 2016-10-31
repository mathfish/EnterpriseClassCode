package thompson.library.system.dtos;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ReservationDto {
    private int reservationid;
    private int patronid;
    private int branchitemid;
    private java.sql.Date reservDate;
    private boolean fulfilled;
    private int forBranchid;

    ReservationDto(){}

    public ReservationDto(int reservationid, int patronid, int branchitemid, Date reservDate, boolean fulfilled,
                          int forBranchid) {
        this.reservationid = reservationid;
        this.patronid = patronid;
        this.branchitemid = branchitemid;
        this.reservDate = reservDate;
        this.fulfilled = fulfilled;
        this.forBranchid = forBranchid;
    }

    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public int getPatronid() {
        return patronid;
    }

    public void setPatronid(int patronid) {
        this.patronid = patronid;
    }

    public int getBranchitemid() {
        return branchitemid;
    }

    public void setBranchitemid(int branchitemid) {
        this.branchitemid = branchitemid;
    }

    public Date getReservDate() {
        return reservDate;
    }

    public void setReservDate(Date reservDate) {
        this.reservDate = reservDate;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public int getForBranchid() {
        return forBranchid;
    }

    public void setForBranchid(int forBranchid) {
        this.forBranchid = forBranchid;
    }
}
