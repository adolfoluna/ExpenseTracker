package com.tracker.expense.db.dto.home;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tracker.expense.db.dto.CategoriaDto;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.model.Categoria;
import com.tracker.expense.db.model.CategoriaHome;

@Stateless
public class CategoriaDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {

	@PersistenceContext private EntityManager entityManager;
	@EJB private CategoriaHome categoriaHome;

	//asigna todos los valores del dto al objeto entidad y regresa el objeto entidad
	public Object fromDtoToInstance(Object dto) {
		
		//validar que el argumento sea de tipo CategoriaDto
		if( !(dto instanceof CategoriaDto) )
			return null;
		
		CategoriaDto cdto = (CategoriaDto) dto;
		
		Categoria instance = new Categoria();
		
		if( cdto.getIdcategoria() > 0 )
			instance.setIdcategoria(cdto.getIdcategoria());
	
		instance.setNombre(cdto.getNombre().trim());
		instance.setVersion(cdto.getVersion());
		
		return instance;
	}
	
	//encontrar la instancia con el id del registro
	public Object getInstance(SearchDto search) {
		return categoriaHome.findById(Integer.valueOf(search.getSearhFieldsMap().get("idcategoria")));
	}

	//hacer query, agregando busquedas y ordenamientos
	public String getDtoQuery(SearchDto search) {
		
		String temp = addFilters(search);
		
		//agregar todos los campo por los que se va a ordenar los resultados
		if( search.getOrderFields() != null && search.getOrderFields().length > 0 ) {
			
			for(String x : search.getOrderFields() ) {
				if( temp.length() <= 0 )
					temp = " order by res."+x;
				else
					temp+=",res."+x;
			}
		}
		
		return "select new com.tracker.expense.db.dto.CategoriaDto(res.idcategoria,res.nombre,res.version) from Categoria res"+temp;
	}
	
	//hacer el query que cuenta el numero de resultados
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Categoria"+addFilters(search);
	}
	
	private String addFilters(SearchDto search) {
		
		HashMap<String, String> aux = search.getSearhFieldsMap();
		
		if( aux != null && aux.containsKey("idcategoria") ) 
			return " where res.idcategoria="+Integer.valueOf(aux.get("idcategoria"));
		
		return "";
	}
	

}
