package thompson.library.system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Reservation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationid;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patron patronid;

    @ManyToOne(cascade = CascadeType.ALL)
    private BranchItem branchItemid;

    @ManyToOne(cascade = CascadeType.ALL)
    private Branch forbranchid;

    private java.sql.Timestamp reservdate;

    private boolean fulfilled;

    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public Patron getPatronid() {
        return patronid;
    }

    public void setPatronid(Patron patronid) {
        this.patronid = patronid;
    }

    public BranchItem getBranchItemid() {
        return branchItemid;
    }

    public void setBranchItemid(BranchItem branchItemid) {
        this.branchItemid = branchItemid;
    }

    public Branch getForbranchid() {
        return forbranchid;
    }

    public void setForbranchid(Branch forbranchid) {
        this.forbranchid = forbranchid;
    }

    public Timestamp getReservdate() {
        return reservdate;
    }

    public void setReservdate(Timestamp reservdate) {
        this.reservdate = reservdate;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
}
