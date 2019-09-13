package com.tracker.expense.db.model;
// Generated Aug 10, 2019, 12:46:42 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TransaccionArticulo.
 * @see com.tracker.expense.db.model.TransaccionArticulo
 * @author Hibernate Tools
 */
@Stateless
public class TransaccionArticuloHome {

    private static final Log log = LogFactory.getLog(TransaccionArticuloHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(TransaccionArticulo transientInstance) {
        log.debug("persisting TransaccionArticulo instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(TransaccionArticulo persistentInstance) {
        log.debug("removing TransaccionArticulo instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public TransaccionArticulo merge(TransaccionArticulo detachedInstance) {
        log.debug("merging TransaccionArticulo instance");
        try {
            TransaccionArticulo result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public TransaccionArticulo findById( TransaccionArticuloId id) {
        log.debug("getting TransaccionArticulo instance with id: " + id);
        try {
            TransaccionArticulo instance = entityManager.find(TransaccionArticulo.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

