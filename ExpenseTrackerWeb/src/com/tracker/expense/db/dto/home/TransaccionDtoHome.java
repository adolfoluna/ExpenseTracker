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
import com.tracker.expense.db.home.TotalUpdaterHome;
import com.tracker.expense.db.model.Cuenta;
import com.tracker.expense.db.model.Proveedor;
import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionHome;
import com.tracker.expense.web.rest.service.OperationRestResult;

@Stateless
@Remote(PersistenceDtoRemote.class)
@Interceptors(MonedaBaseInterceptor.class)
public class TransaccionDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {

	private static final Log log = LogFactory.getLog(TransaccionDtoHome.class);
	@EJB private TransaccionHome transaccionHome;
	@EJB private TotalUpdaterHome totalupdaterHome;
	
	@Override
	public String getDtoQuery(SearchDto search) {
		return "select new com.tracker.expense.db.dto.TransaccionDto("
				+ "t.idtransaccion,"
				+ "t.cuenta.idcuenta,"
				+ "t.cuenta.nombre,"
				+"case when t.proveedor.idproveedor is null then 0 else t.proveedor.idproveedor end,"
				+ "t.proveedor.nombre,"
				+ "t.fecha,"
				+ "t.articulos,"
				+ "t.total,"
				+ "t.tipocambio,"
				+ "t.totalbase,"
				+ "t.ticket,"
				+ "t.pago,"
				+ "t.factura,"
				+ "t.complemento,"
				+ "t.complementorequerido,"
				+ "t.voucher,"
				+ "t.transferencia,"
				+ "t.cheque,"
				+ "t.nota,"
				+ "t.version) from Transaccion t left outer join t.proveedor "+addFilters(search)+" order by date(t.fecha) desc,t.idtransaccion desc";
	}

	@Override
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Transaccion t"+addFilters(search);
	}

	@Override
	public Object fromDtoToInstance(Object dto) {
		
		if(!(dto instanceof TransaccionDto) )
			return null;
		
		double tc = 0;
		
		TransaccionDto tdto = (TransaccionDto) dto;
		Transaccion tx = null;
		
		if( tdto.getIdtransaccion() > 0 ) {
			tx = transaccionHome.findById(tdto.getIdtransaccion());
			//idtransaccion, idcuenta, total nuevo, total anteior, tipo de cambio especificado
			tc = totalupdaterHome.updateExistingTansaction(tdto.getIdtransaccion(),tdto.getIdcuenta(),tx.getCuenta().getIdcuenta(), tdto.getTotal(), tx.getTotal(),tdto.getTipocambio());
		}
		else {
			tx = new Transaccion();
			tc = totalupdaterHome.updateNewTransaction(tdto.getIdcuenta(), tdto.getTotal());
		}
		
		if( tx.getCuenta() == null || tdto.getIdcuenta() != tx.getCuenta().getIdcuenta() ) {
			Cuenta c = new Cuenta();
			c.setIdcuenta(tdto.getIdcuenta());
			tx.setCuenta(c);
		}
		
		//si esta especificado un proveedor, ponerlo, si no poner el proveedor en null
		if( tdto.getIdproveedor() > 0 ) {
			Proveedor p = new Proveedor();
			p.setIdproveedor(tdto.getIdproveedor());
			tx.setProveedor(p);
		}
		else
			tx.setProveedor(null);
		
		tx.setFecha(tdto.getFecha());
		tx.setTotal(tdto.getTotal());
		tx.setTipocambio(tc);
		tx.setTotalbase((long)(tdto.getTotal()*tc));
			
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
	
	@Override
	public OperationRestResult removeDto(SearchDto search) {
		
		Transaccion instance = (Transaccion) getInstance(search);
		
		if( instance == null )
			return new OperationRestResult(false,"instancia no encontrada");
		
		//antes de remover la instancia, actualizar el saldo de la cuenta int idtransaccion,int idcuenta,int oldidcuenta,long total,long oldtotal,double tc
		totalupdaterHome.updateExistingTansaction(instance.getIdtransaccion(),instance.getCuenta().getIdcuenta(), instance.getCuenta().getIdcuenta(), 0, instance.getTotal(),instance.getTipocambio());
		
		//intentar eliminar transaccion
		try {
			transaccionHome.remove(instance);
		}catch(Exception ex) {
			return new OperationRestResult(false, "error al intentar intentar remover instancia "+ex.getMessage());
		}
		
		//regresar que si se pudo realizar la operacion
		return new OperationRestResult(true, null);
				
	}
	
	private String addFilters(SearchDto search) {
		String temp = "";
		
		if( search.getSearhFieldsMap() != null && search.getSearhFieldsMap().containsKey("idtransaccion") )
			return " where t.idtransaccion="+search.getSearhFieldsMap().get("idtransaccion");
		
		//si no hay busqueda avanzada salir
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
					case "transferencia": temp+=" t.transferencia "+field.getComparator()+" "+field.getFieldValue(); break;
					case "cheque": temp+=" t.cheque "+field.getComparator()+" "+field.getFieldValue(); break;
					default: log.info("error campo no encontrado "+field.getFieldName()+" al intentar construir query para busqueda de transaccion"); break;
						
				}
			}
			
			temp+=" )";
		}
		
		//eliminar espacios en blanco al inicio y al final
		temp = temp.trim();

		//quitar el primer and
		if( temp.startsWith("and") )
			temp= temp.substring(3);
		
		//si hubo alguna busqueda agregar la clausula where al inicio de la cadena
		if( temp.length() > 0 )
			temp = " where "+temp;
		
		return temp;
	}
	
}
