package com.tracker.expense.db.model;
// Generated Oct 2, 2019, 10:38:21 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tipocambio.
 * @see com.tracker.expense.db.model.Tipocambio
 * @author Hibernate Tools
 */
@Stateless
public class TipocambioHome {

    private static final Log log = LogFactory.getLog(TipocambioHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Tipocambio transientInstance) {
        log.debug("persisting Tipocambio instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Tipocambio persistentInstance) {
        log.debug("removing Tipocambio instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Tipocambio merge(Tipocambio detachedInstance) {
        log.debug("merging Tipocambio instance");
        try {
            Tipocambio result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Tipocambio findById( TipocambioId id) {
        log.debug("getting Tipocambio instance with id: " + id);
        try {
            Tipocambio instance = entityManager.find(Tipocambio.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

