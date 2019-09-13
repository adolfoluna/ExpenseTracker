package com.tracker.expense.db.dto.home;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tracker.expense.db.dto.CuentaResumenDto;

@Stateless
public class CuentaResumenDtoHome {

	@PersistenceContext private EntityManager entityManager;
	
	public CuentaResumenDtoHome() {
		
	}
	
	public CuentaResumenDto[] getResumen() {
		
		//obetener todas las cuentas ordenadas por tipo y nombre
		Query q = entityManager.createQuery(getCuentaDtoQuery()+" order by c.tipo,c.nombre");
		@SuppressWarnings("unchecked")
		List<CuentaResumenDto> list = q.getResultList();
		
		
		//actualizar balances de la cuenta de semana y mes actual y anterior
		for( CuentaResumenDto c : list ) {
			
			c.setSemanaActual(getWeekBalance(c.getIdcuenta(), 0));
			c.setMesActual(getMonthBalance(c.getIdcuenta(),0));
			
			c.setSemanaAnterior(getWeekBalance(c.getIdcuenta(), 1));
			c.setMesAnterior(getMonthBalance(c.getIdcuenta(), 1));
			
		}
		
		
		//regresar la lista de de todas las cuentas
		return list.toArray(new CuentaResumenDto[0]);
	}
		
	private long getWeekBalance(int idcuenta,int semanas) {
		String aux = "select sum(t.total) as total from Transaccion t where year(t.fecha) = year(date_sub(now(), interval "+semanas+" week))"
					+" and week(t.fecha) = week(date_sub( now(), interval "+semanas+" week)) and t.idcuenta="+idcuenta;
		Query q = entityManager.createNativeQuery(aux);
		BigDecimal b = (BigDecimal) q.getSingleResult();
		
		if(b == null )
			return 0;
		
		return b.longValue();
	}
	
	private long getMonthBalance(int idcuenta,int meses) {

		String aux = "select sum(t.total) as total from Transaccion t where year(t.fecha) = year(date_sub(now(), interval "+meses+" month))"
					+" and month(t.fecha) = month(date_sub( now(), interval "+meses+" month)) and t.idcuenta="+idcuenta;
		Query q = entityManager.createNativeQuery(aux);
		BigDecimal b = (BigDecimal) q.getSingleResult();
		
		if( b == null )
			return 0;
		
		return b.longValue();
	}
	
	//public CuentaResumenDto(int idcuenta, String nombre, long saldo, long saldoMonedaBase, int idmoneda, String moneda,
	//		String tipo,,boolean monedaBase long semanaAnterior, long semanaActual, long mesAnterior, long masActual
	private String getCuentaDtoQuery() {
		return "select new com.tracker.expense.db.dto.CuentaResumenDto("
				+ "c.idcuenta,"
				+ "c.nombre,"
				+ "c.saldo,"
				+ "cast(0 as long),"
				+ "c.moneda.idmoneda,"
				+ "c.moneda.nombre,"
				+ "c.tipo,"
				+ "c.moneda.monedaBase,"
				+ "cast(0 as long),"
				+ "cast(0 as long),"
				+ "cast(0 as long),"
				+ "cast(0 as long)"
				+ ") from Cuenta c";
	}
	
	
}
