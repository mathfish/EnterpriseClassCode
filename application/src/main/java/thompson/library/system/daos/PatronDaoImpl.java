package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.PatronD;
import thompson.library.system.entities.Patron;

import java.util.List;


public class PatronDaoImpl implements PatronDao {
    private static final Logger logger = LoggerFactory.getLogger(PatronDaoImpl.class);
    private SessionFactory sessionFactory;

    PatronDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    /**
     *
     * Returns patron data transfer object using patrons email. Useful to check if patron exists in database prior to
     * insertion.
     */
    @Override
    public PatronD getPatron(String email){
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        List<Patron> list = currentSession.createQuery("select p from Patron p where p.email = :email")
                                          .setParameter("email",email).getResultList();
        PatronD patronD = null;
        if(list.size() == 1){
            Patron patron = list.get(0);
            patronD = new PatronD(patron.getPatronid(), patron.getFirstname(), patron.getLastname(),
                    patron.getCity(), patron.getState(), patron.getZipcode(), patron.getStreetaddress(),
                    patron.getJoindate(), patron.getEmail(), patron.getPhone(), patron.isRemotelibrary(),
                    patron.getPassword());
        }
        if(commitTrans) {
            currentSession.getTransaction().commit();
        }
        return patronD;
    }

    /**
     *
     * Returns patron using the itemReturnOutput object. Part of multiple steps in the item return process
     */
    @Override
    public PatronD getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        Patron patron = currentSession.get(Patron.class,itemReturnOutput.getPatronid());
        PatronD patronD = null;
        if(patron != null){
            patronD = new PatronD(patron.getPatronid(), patron.getFirstname(), patron.getLastname(),
                    patron.getCity(), patron.getState(), patron.getZipcode(), patron.getStreetaddress(),
                    patron.getJoindate(), patron.getEmail(), patron.getPhone(), patron.isRemotelibrary(),
                    patron.getPassword());
        }

        if(commitTrans) {
            currentSession.getTransaction().commit();
        }
        return patronD;
    }

    /**
     *
     * Used to insert patron using the patronD transfer object
     */
    @Override
    public boolean insertPatron(PatronD patronD) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        Patron patron = new Patron();
        patron.setFirstname(patronD.getFirstname());
        patron.setLastname(patronD.getLastname());
        patron.setCity(patronD.getCity());
        patron.setState(patronD.getState());
        patron.setZipcode(patronD.getZipcode());
        patron.setStreetaddress(patronD.getStreetAddress());
        patron.setJoindate(patronD.getJoinDate());
        patron.setPhone(patronD.getPhone());
        patron.setPassword(patronD.getPassword());
        patron.setRemotelibrary(patronD.isRemotelibrary());
        patron.setEmail(patronD.getEmail());
        currentSession.saveOrUpdate(patron);

        if(commitTrans) {
            currentSession.getTransaction().commit();
        }

        return true;
    }



}
