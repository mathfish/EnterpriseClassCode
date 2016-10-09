package thompson.library.system.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.entities.BranchItem;

public class BranchItemDaoImpl implements BranchItemDao {
    private static final Logger logger = LoggerFactory.getLogger(BranchItemDaoImpl.class);
    private SessionFactory sessionFactory;

    BranchItemDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Updates the state of the branch item using the itemReturnOutput. Part of multiple steps of the return process
     */
    @Override
    public void updateBranchItem(BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput) {
        Session currentSession = sessionFactory.getCurrentSession();
        boolean commitTrans = false;
        if(!currentSession.getTransaction().isActive()){
            currentSession.beginTransaction();
            commitTrans = true;
        }

        BranchItem branchItem = currentSession.get(BranchItem.class, itemReturnOutput.getBranchitemid());
        branchItem.setReserved(itemReturnOutput.isReserved());
        branchItem.setCheckedout(false);
        currentSession.saveOrUpdate(branchItem);

        if(commitTrans){
            currentSession.getTransaction().commit();
        }

    }
}
