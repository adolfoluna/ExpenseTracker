package com.tracker.expense.db.dto.home;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.dto.TransaccionArticuloDto;
import com.tracker.expense.db.dto.TransaccionDto;
import com.tracker.expense.db.home.MonedaBaseInterceptor;
import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionArticulo;
import com.tracker.expense.db.model.TransaccionArticuloHome;
import com.tracker.expense.db.model.TransaccionArticuloId;
import com.tracker.expense.db.model.TransaccionHome;
import com.tracker.expense.web.rest.service.OperationRestResult;

@Stateless
@Remote(PersistenceDtoRemote.class)
@Interceptors(MonedaBaseInterceptor.class)
public class TransaccionArticuloDtoHome implements PersistenceDtoRemote {
	
	@PersistenceContext private EntityManager entityManager;

	@EJB private TransaccionArticuloHome transaccionArticuloHome;
	@EJB private TransaccionHome transaccionHome;
	
	private static final Log log = LogFactory.getLog(TransaccionArticuloDtoHome.class);
	
	public OperationRestResult upsertDto(Object dto) {
		
		//veriricar que la instancia sea de tipo TransaccionArticuloDto
		if( !(dto instanceof TransaccionArticuloDto) ) 
			return new OperationRestResult(false, "error, argumento no es de tipo TransaccionArticuloDto, tipo:"+dto.getClass());
		
		//convertir argumento tipo Object a TransaccionArticuloDto
		TransaccionArticuloDto articulodto = (TransaccionArticuloDto) dto;
		
		//actualizar el total de la transaccion
		OperationRestResult res = updateTotal(articulodto.getIdtransaccion(), articulodto.getTotal());
		
		//si hubo algun error al intentar actualizar el total de la transaccion, salir de la rutina
		if( res != null )
			return res;
		
		//validar que el numero de transaccion este especificado
		if( articulodto.getIdtransaccion() <= 0 ) 
			return new OperationRestResult(false,"error, parametros idtransaccion menor o igual a cero");
		
		updateTotal(articulodto.getIdarticulo(),articulodto.getTotal());
		
		//asignar valores a la instancia que se va a guardar en la base de datos
		TransaccionArticuloId id = new TransaccionArticuloId(articulodto.getIdtransaccion(), articulodto.getIdarticulo());
		TransaccionArticulo row = new TransaccionArticulo();
		row.setId(id);
		row.setCantidad(articulodto.getCantidad());
		row.setSubtotal(articulodto.getSubtotal());
		row.setIva(articulodto.getIva());
		row.setTotal(articulodto.getTotal());
		
		try {

			//intentar insertar o actualizar registro
			row = entityManager.merge(row);
			
		}catch(Exception ex) {
			log.error("error al intentar hacer merge de un articulo, msg:"+ex.getMessage());
			return new OperationRestResult(false,"error al intentar actualizar registro de articulo "+ex.getMessage());
		}
		
		//regresar que la operacion fue exitosa
		return new OperationRestResult(true, null);
		
	}
	
	public OperationRestResult removeDto(SearchDto dto) {
		
		HashMap<String, String> temp = dto.getSearhFieldsMap();
		
		if( temp == null ) 
			return new OperationRestResult(false, "error, argumentos de busqueda en null");
		
		if( !temp.containsKey("idtransaccion") ) 
			return new OperationRestResult(false, "error, no se encontro argumento idtransaccion");
		
		if( !temp.containsKey("idarticulo") ) 
			return new OperationRestResult(false, "error, no se encontro argumento idarticulo");
		
		TransaccionArticuloId id = new TransaccionArticuloId(Integer.parseInt(temp.get("idtransaccion")), Integer.parseInt(temp.get("idarticulo")));
		
		try {
			
			//buscar registro
			TransaccionArticulo aux = transaccionArticuloHome.findById(id);
			
			//validar que se haya encontrado el registro
			if( aux == null )
				return new OperationRestResult(false, "registro no encontrado");
			
			//restar el total de la transaccion lo del articulo que se esta eliminando
			updateTotal(aux.getId().getIdtransaccion(), aux.getTotal() * -1);
			
			//intentar eliminar registro de la base de datos
			transaccionArticuloHome.remove(aux);
			
			
		}catch(Exception ex) {
			return new OperationRestResult(false,"error al intentar eliminar registro "+ex.getMessage());
		}
		
		//salir indicando que si se pudo realizar la operacion
		return new OperationRestResult(true,null);
	}

	public OperationRestResult getDto(SearchDto dto) {
		
		HashMap<String, String> temp = dto.getSearhFieldsMap();
		
		if( temp == null ) return new OperationRestResult(false, "error, argumentos de busqueda en null");
		
		if( !temp.containsKey("idtransaccion") ) return new OperationRestResult(false, "error, no se encontro argumento idtransaccion");
		
		//crear query
		Query q = entityManager.createQuery(transaccionArticuloQuery()+" where t.idtransaccion =:idtransaccion");
		
		//poner el parametro de la llave
		q.setParameter("idtransaccion", Integer.valueOf(temp.get("idtransaccion")));
		
		//intentar obtener instancia
		try {
			TransaccionDto res = (TransaccionDto) q.getSingleResult();
			OperationRestResult t = new OperationRestResult(true, null);
			t.setData(res);
			return t;
		}catch(NoResultException ex) {
			log.info("error no se entrontro instancia con idtransaccion "+temp.get("idtransaccion"));
			return new OperationRestResult(false, "error, no se encontro instancia con idtransaccion "+temp.get("idtransaccion"));
		}catch(Exception ex) {
			ex.printStackTrace();
			return new OperationRestResult(false, "error al intentar consultar cuenta con idtransaccion "+temp.get("idtransaccion")+" "+ex.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public OperationRestResult listDto(SearchDto dto) {

		//crear Query para obtener la lista de cuentas ordenadas por tipo y nombre
		Query q = buildQuery(dto);
		
		//objeto que va a contener la lista de resultados
		List<TransaccionArticuloDto> res = null;
		
		//intentar traer la lista de resultados
		try {
			res = (List<TransaccionArticuloDto>) q.getResultList();
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar obtener lista "+ex.getMessage());
		}
		
		//crear una resupuesta exitosa
		OperationRestResult rs = new OperationRestResult(true, null);
		
		//agregar el numero total de resultados, esto es un count(*)
		rs.setResults(res.size());
		
		//agregar los resultados encontrados
		rs.setData(res.toArray(new TransaccionArticuloDto[0]));
		
		//regresar respuesta
		return rs;
	}

	/*public TransaccionArticuloDto(int idtransaccion, int idarticulo, String articuloNombre, float cantidad,
			long subtotal, float iva, long total, int version) {*/
	
	private String transaccionArticuloQuery() {
		return "select new com.tracker.expense.db.dto.TransaccionArticuloDto("
				+ "t.id.idtransaccion,"
				+ "t.id.idarticulo,"
				+ "t.articulo.nombre,"
				+ "t.cantidad,"
				+ "t.subtotal,"
				+ "t.iva,"
				+ "t.total,"
				+ "t.version) from TransaccionArticulo t";
	}
	
	private Query buildQuery(SearchDto search) {
		
		String sql = transaccionArticuloQuery() + " where 1=1";
		
		HashMap<String, String> temp = search.getSearhFieldsMap();
		
		if( temp.containsKey("idtransaccion") ) sql+=" and t.id.idtransaccion=:idtransaccion";
		if( temp.containsKey("idarticulo") ) sql+=" and t.id.idarticulo=:idarticulo";
		
		Query q = entityManager.createQuery(sql);
		
		if( temp.containsKey("idtransaccion") ) q.setParameter("idtransaccion", Integer.parseInt(temp.get("idtransaccion")));
		if( temp.containsKey("idarticulo") ) q.setParameter("idarticulo", Integer.parseInt(temp.get("idarticulo")));
		
		//en caso de estar especificado, limitar resultados
		if( search.getLimite() > 0 ) {
			q.setMaxResults(search.getLimite());
			q.setFirstResult(search.getPagina()*search.getLimite());
		}
		
		return q;
		
	}
	
	private OperationRestResult updateTotal(int idtransaccion,long total) {
		
		try {
			
			//intentar traer la instancia con la transaccion idtransaccion
			Transaccion t = transaccionHome.findById(idtransaccion);
			
			//sumar el total que ya tiene la transaccion mas el total del articulo que se esta agregando
			t.setTotal(t.getTotal()+total);
			
			//guardar cambios en la base de datos
			entityManager.merge(t);
			
			//si regresa null significa que no hubo ningun error
			return null;
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar actualizar total de transaccion "+ex.getMessage());
		}
		
	}
	
}
