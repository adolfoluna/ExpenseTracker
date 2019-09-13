package com.tracker.expense.db.model;
// Generated Aug 10, 2019, 12:46:42 PM by Hibernate Tools 3.2.2.GA


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class GrupoArticulo.
 * @see com.tracker.expense.db.model.GrupoArticulo
 * @author Hibernate Tools
 */
@Stateless
public class GrupoArticuloHome {

    private static final Log log = LogFactory.getLog(GrupoArticuloHome.class);

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(GrupoArticulo transientInstance) {
        log.debug("persisting GrupoArticulo instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(GrupoArticulo persistentInstance) {
        log.debug("removing GrupoArticulo instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public GrupoArticulo merge(GrupoArticulo detachedInstance) {
        log.debug("merging GrupoArticulo instance");
        try {
            GrupoArticulo result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public GrupoArticulo findById( GrupoArticuloId id) {
        log.debug("getting GrupoArticulo instance with id: " + id);
        try {
            GrupoArticulo instance = entityManager.find(GrupoArticulo.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

