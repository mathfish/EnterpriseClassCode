package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.entities.Checkout;

public class CheckoutDaoImpl implements CheckoutDao {
    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
    private SessionFactory sessionFactory;

    CheckoutDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Updates a checkout with the itemReturnOutput object. Part of a many step process for returning an item.
     */
    @Override
    public void updateCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        Checkout checkout =
        (Checkout)currentSession.createQuery("SELECT ch FROM Checkout ch WHERE ch.checkoutid = :id")
                .setParameter("id", itemReturnOutput.getCheckoutid())
                .getSingleResult();

        checkout.setItemsreturned(itemReturnOutput.isReturned());
        currentSession.saveOrUpdate(checkout);
        if(commitTrans){
            currentSession.getTransaction().commit();
        }
    }

    /**
     *
     * Returns a checkout row using the information in the itemReturnOutput object.
     * Part of a many step process for returning an item.
     */
    @Override
    public CheckoutDto getCheckout(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        Checkout checkout =
                (Checkout)currentSession.createQuery("SELECT ch FROM Checkout ch WHERE ch.checkoutid = :id")
                        .setParameter("id", itemReturnOutput.getCheckoutid())
                        .getSingleResult();
        if(commitTrans){
            currentSession.getTransaction().commit();
        }
        return new CheckoutDto(checkout.getCheckoutid(), checkout.getPatronid().getPatronid(),
                checkout.getCheckoutdate(), checkout.getNumberofitems(), checkout.isOverdue(),
                checkout.isItemsreturned());

    }
}
