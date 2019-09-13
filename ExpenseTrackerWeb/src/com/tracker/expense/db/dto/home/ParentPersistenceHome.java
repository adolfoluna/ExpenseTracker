package com.tracker.expense.db.dto.home;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.web.rest.service.OperationRestResult;

public abstract class ParentPersistenceHome {
	
	@PersistenceContext private EntityManager entityManager;
	
	public ParentPersistenceHome() {
		
	}
	
	public abstract String getDtoQuery(SearchDto search);
	
	public abstract String getCountDtoQuery(SearchDto search);
	
	public abstract Object fromDtoToInstance(Object dto);
	
	public abstract Object getInstance(SearchDto search);
	
	public OperationRestResult upsertDto(Object dto) {
		
		//crear entidad y asignarle todos los valores del dto
		Object instance = fromDtoToInstance(dto);
		
		//si la instancia es null entonces salir, significa que el argumento no es del tipo esperado
		if( instance == null ) 
			 return new OperationRestResult(false, "error, argumento no es el esperado, tipo:"+dto.getClass()); 
		
		//intentar guardar cambios
		try {
			entityManager.merge(instance);
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar persistir instancia "+ex.getMessage());
		}
		
		//regresar que si se pudo realizar la operacion
		return new OperationRestResult(true, null);
	}
	
	public OperationRestResult removeDto(SearchDto search) {
		
		Object instance = getInstance(search);
		
		if( instance == null )
			return new OperationRestResult(false,"instancia no encontrada");
		
		//intentar guardar cambios
		try {
			entityManager.remove(instance);
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar intentar remover instancia "+ex.getMessage());
		}
		
		//regresar que si se pudo realizar la operacion
		return new OperationRestResult(true, null);
				
	}
	
	
	public OperationRestResult getDto(SearchDto dto) {
		System.out.println("consultando catalogo:"+dto.getCatalogo());
		//crear query
		Query q = entityManager.createQuery(getDtoQuery(dto));
		
		//intentar obtener instancia
		try {
			Object res = (Object) q.getSingleResult();
			OperationRestResult t = new OperationRestResult(true, null);
			t.setData(res);
			return t;
		}catch(NoResultException ex) {
			return new OperationRestResult(false, "error, no se encontro instancia");
		}catch(Exception ex) {
			ex.printStackTrace();
			return new OperationRestResult(false, "error al intentar consultar instancia");
		}
	}
	
	@SuppressWarnings("unchecked")
	public OperationRestResult listDto(SearchDto dto) {
		
		//crear Query para obtener la lista de resultados
		Query q = entityManager.createQuery(getDtoQuery(dto));
		
		//en caso de estar especificado, limitar resultados
		if( dto.getLimite() > 0 ) {
			q.setMaxResults(dto.getLimite());
			q.setFirstResult(dto.getPagina()*dto.getLimite());
		}
		
		//objeto que va a guardar el resultado del query
		List<Object> res2 = null;
		
		//intentar traer la lista de resultados
		try {
			res2 = q.getResultList();
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar obtener lista "+ex.getMessage());
		}
				
		//crear una resupuesta exitosa
		OperationRestResult rs = new OperationRestResult(true, null);
		
		//agregar el numero total de resultados, esto es un count(*)
		rs.setResults(getResultsDtoCount(dto));
		
		//convertir de lista a arreglo y asignarlo a data del resultado
		rs.setData(res2.toArray());

		//regresar respuesta
		return rs;
	}
	
	private int getResultsDtoCount(SearchDto search) {
		Query q = entityManager.createQuery(getCountDtoQuery(search));
		long count = (long) q.getSingleResult();
		return (int) count;
	}	
	
}
