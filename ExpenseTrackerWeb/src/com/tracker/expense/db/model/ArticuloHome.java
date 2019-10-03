package com.tracker.expense.db.model;
// Generated Oct 2, 2019, 10:38:21 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Articulo.
 * @see com.tracker.expense.db.model.Articulo
 * @author Hibernate Tools
 */
@Stateless
public class ArticuloHome {

    private static final Log log = LogFactory.getLog(ArticuloHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Articulo transientInstance) {
        log.debug("persisting Articulo instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Articulo persistentInstance) {
        log.debug("removing Articulo instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Articulo merge(Articulo detachedInstance) {
        log.debug("merging Articulo instance");
        try {
            Articulo result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Articulo findById( Integer id) {
        log.debug("getting Articulo instance with id: " + id);
        try {
            Articulo instance = entityManager.find(Articulo.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

