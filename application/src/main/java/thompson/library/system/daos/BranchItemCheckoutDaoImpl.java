package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.entities.BranchItemCheckout;
import thompson.library.system.entities.Checkout;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


public class BranchItemCheckoutDaoImpl implements BranchItemCheckoutDao {

    private static final Logger logger = LoggerFactory.getLogger(BranchItemCheckoutDaoImpl.class);
    private SessionFactory sessionFactory;


    BranchItemCheckoutDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    /**
     *
     * Returns information on the branch item for a checkout. Returns branchItemDto
     */
    @Override
    public BranchItemCheckoutDto getBranchItemCheckout(BranchItemDto branchItemDto) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        List<BranchItemCheckout> branchItemCheckouts =
        currentSession.createQuery("SELECT bic from BranchItemCheckout bic where bic.branchitemid.branchitemid = :bid and " +
                                    "bic.returned = :ret")
                      .setParameter("bid", branchItemDto.getBranchitemid())
                      .setParameter("ret", false)
                      .getResultList();
        BranchItemCheckoutDto dto = null;
        if(branchItemCheckouts.size() == 1){
            BranchItemCheckout branchItemCheckout = branchItemCheckouts.get(0);
            dto = new BranchItemCheckoutDto(branchItemCheckout.getCheckoutid().getCheckoutid(),
                    branchItemCheckout.getBranchitemid().getBranchitemid(), branchItemCheckout.isOverdue(),
                    branchItemCheckout.getDuedate(), branchItemCheckout.isRenew(), branchItemCheckout.getRenewdate(),
                    branchItemCheckout.isReturned(), branchItemCheckout.getRenewdate());
        }
        if(commitTrans){
            currentSession.getTransaction().commit();
        }

        return dto;
    }

    /**
     *
     * Updates a branch item checkout using transfer object branchItemCheckoutDto. Beginning of multi-step transaction
     */
    @Override
    public BranchItemCheckoutDao.ItemReturnOutput updateBranchItemCheckout(BranchItemCheckoutDto branchItemCheckoutDto) {
        Session currentSession = sessionFactory.getCurrentSession();

        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
        }

        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTime().getTime());
        BranchItemCheckout result =
        (BranchItemCheckout)currentSession.createQuery("SELECT bic from BranchItemCheckout bic where " +
                "bic.branchitemid.branchitemid = :bid and " +
                "bic.checkoutid.checkoutid = :cid")
                .setParameter("bid", branchItemCheckoutDto.getBranchItemID())
                .setParameter("cid", branchItemCheckoutDto.getCheckoutID())
                .getSingleResult();
        result.setReturned(true);
        result.setReturndate(date);
        result.setOverdue(branchItemCheckoutDto.isOverdue());
        currentSession.saveOrUpdate(result);

        return new ItemReturnOutput(branchItemCheckoutDto.getCheckoutID(),
                    branchItemCheckoutDto.getBranchItemID(), false);

    }

    /**
     *
     * Returns the total number of items that have been returned from a checkout. That is a checkout can have multiple
     * items, each of which of can be returned at different times.
     */

    @Override
    public int getNumberOfItemsReturnedFromCheckout(ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        List<Checkout> checkouts =
        currentSession.createQuery("SELECT bic from BranchItemCheckout bic WHERE bic.checkoutid.checkoutid = :cid AND bic.returned = :ret")
                .setParameter("cid",itemReturnOutput.getCheckoutid())
                .setParameter("ret",true)
                .getResultList();

        if(commitTrans){
            currentSession.getTransaction().commit();
        }
        return checkouts.size();

    }

}
