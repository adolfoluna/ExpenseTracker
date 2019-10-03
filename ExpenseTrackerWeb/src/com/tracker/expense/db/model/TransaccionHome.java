package com.tracker.expense.db.model;
// Generated Oct 2, 2019, 10:38:21 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Transaccion.
 * @see com.tracker.expense.db.model.Transaccion
 * @author Hibernate Tools
 */
@Stateless
public class TransaccionHome {

    private static final Log log = LogFactory.getLog(TransaccionHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Transaccion transientInstance) {
        log.debug("persisting Transaccion instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Transaccion persistentInstance) {
        log.debug("removing Transaccion instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Transaccion merge(Transaccion detachedInstance) {
        log.debug("merging Transaccion instance");
        try {
            Transaccion result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Transaccion findById( Integer id) {
        log.debug("getting Transaccion instance with id: " + id);
        try {
            Transaccion instance = entityManager.find(Transaccion.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

