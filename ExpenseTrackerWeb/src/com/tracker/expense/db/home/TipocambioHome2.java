package com.tracker.expense.db.home;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tracker.expense.db.model.Cuenta;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Tipocambio;
import com.tracker.expense.db.model.TipocambioHome;

@Stateless
public class TipocambioHome2 extends TipocambioHome {
	
	@PersistenceContext private EntityManager entityManager;
	@EJB private MonedaBaseHome monedabaseHome;
	
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
	
	public double getTipocambioAMonedabase(int idcuenta) {
		
		//si la cuenta a consultar tiene la moneda base entonces regresar 1
		if( monedabaseHome.isCuentaMonedaBase(idcuenta) )
			return 1;
		
		//buscar la cuenta
		Query q = entityManager.createQuery("select c from Cuenta c join c.moneda m where c.idcuenta=:idcuenta");
		q.setParameter("idcuenta", idcuenta);
		Cuenta cuenta = (Cuenta) q.getSingleResult();
		
		if( cuenta == null )
			return 0;
		
		Tipocambio tc = getTipocambio(cuenta.getMoneda().getIdmoneda(), MonedaBaseValues.getMonedaBase());
		
		if( tc == null )
			return 0;
		
		return tc.getTipocambio();
	}

}
