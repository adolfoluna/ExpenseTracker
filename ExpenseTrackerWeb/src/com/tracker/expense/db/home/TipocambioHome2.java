package com.tracker.expense.db.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Tipocambio;
import com.tracker.expense.db.model.TipocambioHome;

@Stateless
public class TipocambioHome2 extends TipocambioHome {
	
	@PersistenceContext private EntityManager entityManager;
	
	//private static final Log log = LogFactory.getLog(TipocambioHome2.class);
	
	public TipocambioHome2() {
		
	}
	
	public Tipocambio getTipocambio(int monedaOrigen,int monedaDestino) {
		Query q = entityManager.createQuery("from Tipocambio t where t.monedaByIdmonedaOrigen.idmoneda=:origen and t.monedaByIdmonedaDestino.idmoneda=:destino");
		q.setParameter("origen", monedaOrigen);
		q.setParameter("destino",monedaDestino);
		List<?> res = (List<?>) q.getResultList();
		
		if( res.size() <= 0 )
			return null;
		
		return (Tipocambio) res.get(0);
				
	}

}
