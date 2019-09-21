package com.tracker.expense.db.home;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Cuenta;
import com.tracker.expense.db.model.Moneda;

@Stateless
public class MonedaBaseHome {

	private static final Log log = LogFactory.getLog(MonedaBaseHome.class);
	
	@PersistenceContext private EntityManager entityManager;
	
	@EJB private TipocambioHome2 tipocambioHome;
	
	private static HashMap<Integer,Boolean> cuentas = new HashMap<Integer,Boolean>();
	
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
		
		//borrar todos los valores
		cuentas.clear();
		MonedaBaseValues.setMonedaBase(m.getIdmoneda());
		MonedaBaseValues.setMonedaBaseNombre(m.getNombre());
	}
	
	public void clear() {
		//borrar todos los valores
		cuentas.clear();
		MonedaBaseValues.clear();
	}
	
	public boolean isCuentaMonedaBase(int idcuenta) {
		
		//si no esta la cuenta en el mapa, agregarla
		if( !cuentas.containsKey(idcuenta) ) 
				addCuenta(idcuenta);
		
		//si sigue sin estar significa que no se encontro
		if( !cuentas.containsKey(idcuenta) ) {
			log.error("error cuenta con idcuenta:"+idcuenta+" no encontrada");
			return false;
		}
		
		return cuentas.get(idcuenta);
	}
	
	private void addCuenta(int idcuenta) {
		
		//buscar en la tabla cuenta, la cuenta especificada haciendo un inner join a moneda
		Query q = entityManager.createQuery("select c from Cuenta c join c.moneda m where c.idcuenta=:idcuenta");
		q.setParameter("idcuenta", idcuenta);
				
		//ejecutar consulta
		Cuenta c = (Cuenta) q.getSingleResult();
		
		//agregar al mapa la moneda base
		if( c.getMoneda().getMonedaBase() > 0 )
			cuentas.put(idcuenta, true);
		else
			cuentas.put(idcuenta, false);

	}
	
	
}
