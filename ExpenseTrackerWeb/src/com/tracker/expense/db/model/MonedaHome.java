package com.tracker.expense.db.model;
// Generated Aug 10, 2019, 12:46:42 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Moneda.
 * @see com.tracker.expense.db.model.Moneda
 * @author Hibernate Tools
 */
@Stateless
public class MonedaHome {

    private static final Log log = LogFactory.getLog(MonedaHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Moneda transientInstance) {
        log.debug("persisting Moneda instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Moneda persistentInstance) {
        log.debug("removing Moneda instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Moneda merge(Moneda detachedInstance) {
        log.debug("merging Moneda instance");
        try {
            Moneda result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Moneda findById( Integer id) {
        log.debug("getting Moneda instance with id: " + id);
        try {
            Moneda instance = entityManager.find(Moneda.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

