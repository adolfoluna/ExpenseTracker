package com.tracker.expense.db.dto.home;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.AdvancedSearchDtoField;
import com.tracker.expense.db.dto.AdvancedSearchDtoGroup;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.dto.TransaccionDto;
import com.tracker.expense.db.home.MonedaBaseInterceptor;
import com.tracker.expense.db.model.Cuenta;
import com.tracker.expense.db.model.Proveedor;
import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionHome;

@Stateless
@Remote(PersistenceDtoRemote.class)
@Interceptors(MonedaBaseInterceptor.class)
public class TransaccionDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {

	private static final Log log = LogFactory.getLog(TransaccionDtoHome.class);
	@EJB private TransaccionHome transaccionHome;
	
	@Override
	public String getDtoQuery(SearchDto search) {
		return "select new com.tracker.expense.db.dto.TransaccionDto("
				+ "t.idtransaccion,"
				+ "t.cuenta.idcuenta,"
				+ "t.cuenta.nombre,"
				+ "t.proveedor.idproveedor,"
				+ "t.proveedor.nombre,"
				+ "t.fecha,"
				+ "t.articulos,"
				+ "t.total,"
				+ "t.tipocambio,"
				+ "t.ticket,"
				+ "t.pago,"
				+ "t.factura,"
				+ "t.complemento,"
				+ "t.complementorequerido,"
				+ "t.voucher,"
				+ "t.nota,"
				+ "t.version) from Transaccion t "+addFilters(search)+" order by date(t.fecha) desc,t.idtransaccion desc";
	}

	@Override
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Transaccion t"+addFilters(search);
	}

	@Override
	public Object fromDtoToInstance(Object dto) {
		
		if(!(dto instanceof TransaccionDto) )
			return null;
		
		TransaccionDto tdto = (TransaccionDto) dto;
		Transaccion tx = null;
		
		if( tdto.getIdtransaccion() > 0 )
			tx = transaccionHome.findById(tdto.getIdtransaccion());
		else
			tx = new Transaccion();
		
		
		if( tx.getCuenta() == null || tdto.getIdcuenta() != tx.getCuenta().getIdcuenta() ) {
			Cuenta c = new Cuenta();
			c.setIdcuenta(tdto.getIdcuenta());
			tx.setCuenta(c);
		}
		
		if( tx.getProveedor() == null || tdto.getIdproveedor() != tx.getProveedor().getIdproveedor() ) {
			Proveedor p = new Proveedor();
			p.setIdproveedor(tdto.getIdproveedor());
			tx.setProveedor(p);
		}
		
		tx.setFecha(tdto.getFecha());
		tx.setTotal(tdto.getTotal());
		
		if( tdto.getNota() != null && tdto.getNota().trim().length() > 0 )
			tx.setNota(tdto.getNota().trim());
		else 
			tx.setNota(null);
		
		tx.setVersion(tdto.getVersion());
		
		if( tdto.isComplementoRequerido())	tx.setComplementorequerido((byte)1);
		else tx.setComplementorequerido((byte)0);
		
		return tx;
	}

	@Override
	public Object getInstance(SearchDto search) {
		Transaccion c = transaccionHome.findById(Integer.valueOf(search.getSearhFieldsMap().get("idtransaccion")));
		return c;
	}
	
	private String addFilters(SearchDto search) {
		String temp = " where 1=1";
		
		if( search.getSearhFieldsMap() != null && search.getSearhFieldsMap().containsKey("idtransaccion") )
			return " where t.idtransaccion="+search.getSearhFieldsMap().get("idtransaccion");
		
		//si no hay busqueda de avanzada salir
		if( search.getAdvancedSearchGroups() == null || search.getAdvancedSearchGroups().length <= 0 )
			return temp;
		
		for( AdvancedSearchDtoGroup group : search.getAdvancedSearchGroups() ) {
			
			if( group == null )
				continue;
			
			if( group.getUnion() != null )
				temp+=" "+group.getUnion();
			else
				temp+=" and ";
			
			temp+=" (";
			
			for(AdvancedSearchDtoField field : group.getFields() ) {
				
				if( field.getUnion() != null ) temp+=" "+field.getUnion();
				
				switch(field.getFieldName()) {
					case "proveedor": temp+=" t.proveedor.idproveedor"+field.getComparator()+Integer.valueOf(field.getFieldValue()); break; 
					case "idtransaccion": temp+=" t.idtransaccion"+field.getComparator()+Integer.valueOf(field.getFieldValue()); break;
					case "articulo": temp+=" a.id.idarticulo"+field.getComparator()+Integer.valueOf(field.getFieldValue()); break;
					case "ticket": temp+=" t.ticket "+field.getComparator()+" "+field.getFieldValue(); break;
					case "factura": temp+=" t.factura "+field.getComparator()+" "+field.getFieldValue(); break;
					case "complemento": temp+=" t.complemento "+field.getComparator()+" "+field.getFieldValue(); break;
					case "voucher": temp+=" t.voucher "+field.getComparator()+" "+field.getFieldValue(); break;
					case "requierecomplemento": temp+=" t.complementorequerido > 0"; break;
					case "fecha":  temp+=" date(t.fecha) "+field.getComparator()+" '"+field.getFieldValue()+"'"; break;
					case "idcuenta": temp+=" t.cuenta.idcuenta"+field.getComparator()+Integer.valueOf(field.getFieldValue()); break;
					default: log.info("error campo no encontrado "+field.getFieldName()+" al intentar construir query para busqueda de transaccion"); break;
						
				}
			}
			
			temp+=" )";
		}

		
		return temp;
	}
	
}
