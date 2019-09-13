package com.tracker.expense.db.dto.home;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tracker.expense.db.dto.ProveedorDto;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.model.Proveedor;
import com.tracker.expense.db.model.ProveedorHome;

@Stateless
@Remote(PersistenceDtoRemote.class)
public class ProveedorDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {

	@PersistenceContext private EntityManager entityManager;
	@EJB private ProveedorHome proveedorHome;
	
	@Override
	public String getDtoQuery(SearchDto search) {
		return "select new com.tracker.expense.db.dto.ProveedorDto(p.idproveedor,p.nombre,p.version) from Proveedor p"+addFilters(search)+addOrderFields(search);
	}

	@Override
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Proveedor p"+addFilters(search);
	}

	@Override
	public Object fromDtoToInstance(Object dto) {
		
		if( !(dto instanceof ProveedorDto) )
			return null;
		
		ProveedorDto pdto = (ProveedorDto) dto;
		
		Proveedor proveedor = new Proveedor();
		
		if( pdto.getIdproveedor() > 0 )
			proveedor.setIdproveedor(pdto.getIdproveedor());
		
		proveedor.setNombre(pdto.getNombre().trim());
		proveedor.setVersion(pdto.getVersion());
		
		return proveedor;
	}

	@Override
	public Object getInstance(SearchDto search) {
		Proveedor p = proveedorHome.findById(Integer.valueOf(search.getSearhFieldsMap().get("idproveedor")));
		return p;
	}
	
	private String addFilters(SearchDto search) {
		if( search.getSearhFieldsMap() != null && search.getSearhFieldsMap().containsKey("idproveedor") )
			return " where p.idproveedor="+Integer.valueOf(search.getSearhFieldsMap().get("idproveedor"));
		return "";
	}
	
	private String addOrderFields(SearchDto search) {
		if( search.isOrderByField("nombre") ) return " order by p.nombre";
		else return " order by p.idproveedor";
	}

}
