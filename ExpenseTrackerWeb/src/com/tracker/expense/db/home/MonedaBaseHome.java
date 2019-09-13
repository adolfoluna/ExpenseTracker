package com.tracker.expense.db.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Moneda;

@Stateless
public class MonedaBaseHome {

	private static final Log log = LogFactory.getLog(MonedaBaseHome.class);
	
	private static int monedaBase = 0;
	private static String monedaBaseNombre = "";
	
	@PersistenceContext private EntityManager entityManager;
	
	public MonedaBaseHome() {
		
	}
	
	public void setMonedaBase() {
		
		log.info("consultando moneda base del sistema......");
		
		Query q = entityManager.createQuery("from Moneda m where m.monedaBase > 0");
		
		List<?> res = (List<?>) q.getResultList();
		
		if( res == null || res.size() <= 0 ) {
			log.error("error no se encontro la moneda base en la tabla moneda");
			return;
		}
		
		Moneda m = (Moneda) res.get(0);
		
		if( m.getIdmoneda() == null ) {
			log.error("error moneda en null.........");
			return;
		}
		
		log.info("poniendo como moneda base "+m.getIdmoneda()+" nombre:"+m.getNombre());
		
		monedaBase = m.getIdmoneda();
		monedaBaseNombre = m.getNombre();
	}
	
	public static int getMonedaBase() {
		return monedaBase;
	}
	
	public static String getMonedaBaseNombre() {
		return monedaBaseNombre;
	}
	
	public static void clear() {
		monedaBase = 0;
		monedaBaseNombre = null;
	}
	
	
}
