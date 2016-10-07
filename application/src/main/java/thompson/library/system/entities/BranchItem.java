package thompson.library.system.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
public class BranchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branchitemid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchItemid")
    private Set<Reservation> reservations;

    @ManyToOne(cascade = CascadeType.ALL)
    private Branch branchid;

    @ManyToOne(cascade = CascadeType.ALL)
    private Branch currentlocation;

    private boolean checkedout;

    private boolean reserved;

    private boolean intransit;



}
