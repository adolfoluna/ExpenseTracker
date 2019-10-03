package com.tracker.expense.db.model;
// Generated Oct 2, 2019, 10:38:21 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Categoria.
 * @see com.tracker.expense.db.model.Categoria
 * @author Hibernate Tools
 */
@Stateless
public class CategoriaHome {

    private static final Log log = LogFactory.getLog(CategoriaHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Categoria transientInstance) {
        log.debug("persisting Categoria instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(Categoria persistentInstance) {
        log.debug("removing Categoria instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public Categoria merge(Categoria detachedInstance) {
        log.debug("merging Categoria instance");
        try {
            Categoria result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Categoria findById( Integer id) {
        log.debug("getting Categoria instance with id: " + id);
        try {
            Categoria instance = entityManager.find(Categoria.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

