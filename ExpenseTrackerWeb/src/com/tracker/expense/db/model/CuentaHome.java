package com.tracker.expense.db.model;
// Generated Oct 2, 2019, 10:38:21 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Cuenta.
 * @see com.tracker.expense.db.model.Cuenta
 * @author Hibernate Tools
 */
@Stateless
public class CuentaHome {

    private static final Log log = LogFactory.getLog(CuentaHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Cuenta transientInstance) {
        log.debug("persisting Cuenta instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Cuenta persistentInstance) {
        log.debug("removing Cuenta instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Cuenta merge(Cuenta detachedInstance) {
        log.debug("merging Cuenta instance");
        try {
            Cuenta result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Cuenta findById( Integer id) {
        log.debug("getting Cuenta instance with id: " + id);
        try {
            Cuenta instance = entityManager.find(Cuenta.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

