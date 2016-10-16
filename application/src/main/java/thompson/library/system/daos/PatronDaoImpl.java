package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.dtos.PatronDto;
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
    public PatronDto getPatron(String email){
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        List<Patron> list = currentSession.createQuery("select p from Patron p where p.email = :email")
                                          .setParameter("email",email).getResultList();
        PatronDto patronDto = null;
        if(list.size() == 1){
            Patron patron = list.get(0);
            patronDto = new PatronDto(patron.getPatronid(), patron.getFirstname(), patron.getLastname(),
                    patron.getCity(), patron.getState(), patron.getZipcode(), patron.getStreetaddress(),
                    patron.getJoindate(), patron.getEmail(), patron.getPhone(), patron.isRemotelibrary(),
                    patron.getPassword());
        }
        if(commitTrans) {
            currentSession.getTransaction().commit();
        }
        return patronDto;
    }

    /**
     *
     * Returns patron using the itemReturnOutput object. Part of multiple steps in the item return process
     */
    @Override
    public PatronDto getPatron(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        Patron patron = currentSession.get(Patron.class,itemReturnOutput.getPatronid());
        PatronDto patronDto = null;
        if(patron != null){
            patronDto = new PatronDto(patron.getPatronid(), patron.getFirstname(), patron.getLastname(),
                    patron.getCity(), patron.getState(), patron.getZipcode(), patron.getStreetaddress(),
                    patron.getJoindate(), patron.getEmail(), patron.getPhone(), patron.isRemotelibrary(),
                    patron.getPassword());
        }

        if(commitTrans) {
            currentSession.getTransaction().commit();
        }
        return patronDto;
    }

    /**
     *
     * Used to insert patron using the patronDto transfer object
     */
    @Override
    public boolean insertPatron(PatronDto patronDto) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }
        Patron patron = new Patron();
        patron.setFirstname(patronDto.getFirstname());
        patron.setLastname(patronDto.getLastname());
        patron.setCity(patronDto.getCity());
        patron.setState(patronDto.getState());
        patron.setZipcode(patronDto.getZipcode());
        patron.setStreetaddress(patronDto.getStreetAddress());
        patron.setJoindate(patronDto.getJoinDate());
        patron.setPhone(patronDto.getPhone());
        patron.setPassword(patronDto.getPassword());
        patron.setRemotelibrary(patronDto.isRemotelibrary());
        patron.setEmail(patronDto.getEmail());
        currentSession.saveOrUpdate(patron);

        if(commitTrans) {
            currentSession.getTransaction().commit();
        }

        return true;
    }



}
