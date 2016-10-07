import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ObjectNameSource;
import org.hibernate.query.Query;
import thompson.library.system.entities.Patron;

import java.util.List;
import java.util.Optional;


public class driver {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("derby.cfg.xml").buildSessionFactory();
        Session currentSession = factory.getCurrentSession();

        if(!currentSession.getTransaction().isActive()){
            System.out.println("begin");
            currentSession.beginTransaction();
        }
        Patron patron = currentSession.get(Patron.class, 3);



        Patron patron1 = currentSession.get(Patron.class,1);
//      List<Patron> list = currentSession.createQuery("select p from Patron p where p.email = :email")
//                .setParameter("email","mj@email.com").getResultList();
//        if(list.size() == 0 ){
//            System.out.println("empty");
//        }else{
//            System.out.println(list.get(0).getCity());
//        }
        if(patron != null) {
            System.out.println(patron.getFirstname());
        } else{
            System.out.println("null");
        }

        currentSession.getTransaction().commit();

    }
}
