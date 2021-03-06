package com.tracker.expense.db.home;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionHome;


@Stateless
public class TotalUpdaterHome {

	private static final Log log = LogFactory.getLog(TotalUpdaterHome.class);
	@PersistenceContext private EntityManager entityManager;
	@EJB private TipocambioHome2 tipocambioHome;
	@EJB private MonedaBaseHome monedabaseHome;
	@EJB private TransaccionHome transaccionHome;
	
	public TotalUpdaterHome() {
		
	}
	
	public double updateNewTransaction(int idcuenta,long total) {
		
		//actualizar el saldo de la cuenta a la que pertenece la transaccion sumando el total de la transaccion
		updateSaldo(idcuenta,total);
		
		//consultar el tipo de cambio dee la moneda de la transaccion a la moneda base del sistema
		//en caso de que la moneda base sea igual a la moneda de la transaccion regresa 1
		return tipocambioHome.getTipocambioAMonedabase(idcuenta);
		
	}
	
	public double updateExistingTansaction(int idtransaccion,int idcuenta,int oldidcuenta,long total,long oldtotal,double tc) {
		
		//si no hay cambio de cuenta en la transaccion
		if( idcuenta == oldidcuenta ) {
			
			//actualizar el saldo de la cuenta sumando el actual y restando el anterior
			updateSaldo(idcuenta, total-oldtotal);
			
			//la transaccion no es nueva por lo tanto regresar el mismo tipo de cambio especificado en el argumento
			return tc;
			
		}
		
		//consultar el tipo de cambio que hay de la moneda de la transaccion a la moneda base del sistema
		tc = tipocambioHome.getTipocambioAMonedabase(idcuenta);
		
		//actualizar el totalbase de todos los articulos que pertenescan a esta transaccion
		updateArticulos(idtransaccion,tc);
			
		//restar el total de la transaccion al saldo de la cuenta a la que pertenecia la transaccion
		updateSaldo(oldidcuenta, oldtotal*-1);
			
		//sumar el total de la transaccion al saldo de la cuenta a la que va a pertenecer la transaccion
		updateSaldo(idcuenta,total);
		
		//regresar el tipo de cambio consultado
		return tc;
		
	}
	
	public double updateTransactionItem(int idtransaccion,long total) {
		
		//intentar traer la instancia con la transaccion idtransaccion
		Transaccion t = transaccionHome.findById(idtransaccion);
		
		//si no se encontro la transaccion marcar error y salir
		if( t == null ) {
			log.error("error transaccion no encontrada "+idtransaccion);
			return 0;
		}
		
		//sumar el total que ya tiene la transaccion mas el total del articulo que se esta agregando
		t.setTotal(t.getTotal()+total);
		
		//actualizar el total base de la transaccion que es lo que tenia + (total * tipo de cambio)
		t.setTotalbase((long) ( t.getTotalbase() + (t.getTipocambio()*total)  ) );
		
		//guardar cambios a los totales de la transaccion
		entityManager.merge(t);
		
		//actualizar el saldo de la cuenta a la que pertenece la transaccion
		updateSaldo(t.getCuenta().getIdcuenta(), total);
		
		//regresar el tipo de cambio de la transaccion
		return t.getTipocambio();
	}
	
	private void updateSaldo(int idcuenta,long total) {
		
		//si el total es cero entonces no es necesario actualizar ningun saldo
		if( total == 0 )
			return;
		
		log.info("tasksaldo agregando saldo a cuenta:"+idcuenta+" monto:"+total);
		
		//hacer query que actualize el saldo
		Query q = entityManager.createQuery("update Cuenta c set saldo=saldo+:total where c.idcuenta=:idcuenta");
		
		//poner los valores de los parametros del query
		q.setParameter("total", total);
		q.setParameter("idcuenta", idcuenta);
		
		//indicar si el query modifico el registro con exito
		if( q.executeUpdate() > 0 )
			log.info("tasksaldo saldo exisotamente agregado a cuenta:"+idcuenta+" monto:"+total);
		else
			log.error("tasksaldo error al intentar agregar saldo cuenta:"+idcuenta+" monto:"+total);
	}
	
	private void updateArticulos(int idtransaccion,double tc) {
		
		//construir el query para consultar todos los articulos que pertenescan a la transaccion especificada
		Query q = entityManager.createQuery("update TransaccionArticulo a set a.totalbase=cast(a.total as double) * cast(:tc as double) where a.id.idtransaccion=:idtransaccion");
		
		//poner el parametro idtransaccion y el de tipo de cambio
		q.setParameter("idtransaccion", idtransaccion);
		q.setParameter("tc", tc);
		
		//ejecutar actualizacion
		log.info("totalbase exitosamente actualizados idtransaccion:"+idtransaccion+" registros:"+q.executeUpdate());
		
	}
}
