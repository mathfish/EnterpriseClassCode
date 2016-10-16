package thompson.library.system.daos;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import thompson.library.system.dtos.PatronD;
import thompson.library.system.entities.Patron;
import thompson.library.system.utilities.ConnectionManager;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class PatronDaoTest {
    private SessionFactory sessionFactory;
    private java.sql.Timestamp joinDate;

    @Before
    public void setSessionFactory(){
        this.sessionFactory = ConnectionManager.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
    }


    @Test
    public void testPatronDaoInsert(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        PatronDaoImpl impl = new PatronDaoImpl(sessionFactory);

        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        PatronD patronD = new PatronD("testFirst", "testLast", "testCity", "ST", 99999,
                "testStreetAddress",joinDate, "test@email.test", 9999999999L,false, "testPW");

        impl.insertPatron(patronD);

        Patron patronReturn = (Patron)session.createQuery("select p from Patron p where p.email = :email")
                .setParameter("email","test@email.test")
                .getSingleResult();
        assertEquals(patronReturn.getFirstname(), patronD.getFirstname());

        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void testGetPatronByEmail(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        PatronDaoImpl impl = new PatronDaoImpl(sessionFactory);
        insertTestPatron(session);

        PatronD dto = impl.getPatron("test@email.test");
        verifyDto(dto);

        session.getTransaction().rollback();
        session.close();

    }

    @Test
    public void getPatronByItemReturnOutput(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        PatronDaoImpl impl = new PatronDaoImpl(sessionFactory);
        insertTestPatron(session);

        Patron patron = (Patron)session.createQuery("select p from Patron p where p.email = :email")
                .setParameter("email","test@email.test").getSingleResult();

        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput =
                new BranchItemCheckoutDao.ItemReturnOutput(null, null, false);
        itemReturnOutput.setPatronid(patron.getPatronid());
        PatronD dto = impl.getPatron(itemReturnOutput);
        verifyDto(dto);
        session.getTransaction().rollback();
        session.close();

    }

    private void insertTestPatron(Session session){
        Patron patron = new Patron();
        patron.setFirstname("testFirst");
        patron.setLastname("testLast");
        patron.setCity("testCity");
        patron.setState("CC");
        patron.setZipcode(99999);
        patron.setStreetaddress("testAddress");
        patron.setJoindate(joinDate);
        patron.setPhone(9999999999L);
        patron.setPassword("testPW");
        patron.setRemotelibrary(false);
        patron.setEmail("test@email.test");
        session.saveOrUpdate(patron);
    }

    private void verifyDto(PatronD dto){
        assertEquals("testFirst",  dto.getFirstname());
        assertEquals("testLast", dto.getLastname());
        assertEquals("testCity", dto.getCity());
        assertEquals("CC", dto.getState());
        assertEquals(99999,dto.getZipcode());
        assertEquals("testAddress", dto.getStreetAddress());
        assertEquals(joinDate, dto.getJoinDate());
        assertEquals(9999999999L, dto.getPhone());
        assertEquals("testPW", dto.getPassword());
        assertFalse(dto.isRemotelibrary());
        assertEquals("test@email.test", dto.getEmail());
    }

}
