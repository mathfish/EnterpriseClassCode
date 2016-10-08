package thompson.library.system.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BranchItemCheckoutId implements Serializable{

    private int branchitemid;

    private int checkoutid;

}
