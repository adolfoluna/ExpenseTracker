package com.tracker.expense.db.dto.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tracker.expense.db.dto.GraphDataDto;

@Stateless
public class GraphDataDtoHome {
	
	@PersistenceContext private EntityManager entityManager;
	
	public List<GraphDataDto> getTransactionsSumByMonth(int limit,int year) {
		
		//crear el query
		Query q = null;
		
		if( year <= 0 )
			q = entityManager.createQuery("select new com.tracker.expense.db.dto.GraphDataDto(DATE_FORMAT(t.fecha, '%Y-%m'),sum(t.totalbase)) from Transaccion t"
				+ " group by DATE_FORMAT(t.fecha, '%Y-%m') order by  DATE_FORMAT(t.fecha, '%Y-%m') desc" );
		else {
			q = entityManager.createQuery("select new com.tracker.expense.db.dto.GraphDataDto(DATE_FORMAT(t.fecha, '%Y-%m'),sum(t.totalbase)) from Transaccion t"
					+ " where year(t.fecha)=:year group by DATE_FORMAT(t.fecha, '%Y-%m') order by DATE_FORMAT(t.fecha, '%Y-%m') desc" );
			q.setParameter("year", year);
		}
				
		//ponerle limite a la cantidad de resultados
		q.setMaxResults(limit);
		
		//consultar la base de datos
		@SuppressWarnings("unchecked")
		List<GraphDataDto> res = q.getResultList();
		
		//regresar el resultado
		return res;
	}

}
