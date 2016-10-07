package thompson.library.system.entities;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationid;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patron patronid;

    @ManyToOne(cascade = CascadeType.ALL)
    private BranchItem branchItemid;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reservation")
    private Branch forbranchid;

    private java.sql.Timestamp reservdate;

    private boolean fulfilled;

}
