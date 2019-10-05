package com.tracker.expense.db.dto.home;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionHome;
import com.tracker.expense.web.rest.service.OperationRestResult;

@Stateless
@Remote(TransaccionAttachmentRemote.class)
public class TransaccionAttachmentHome implements TransaccionAttachmentRemote {

	private final static Log log = LogFactory.getLog(TransaccionAttachmentHome.class);
	
	@EJB private TransaccionHome transaccionHome;
	
	public OperationRestResult addAttachment(int idtransaccion,String tipoComprobante, String fileName) {
		
		try {
			
			log.info("intentando agregar "+fileName+" al campo "+tipoComprobante+" de la transaccion "+idtransaccion);
			
			Transaccion tr = transaccionHome.findById(idtransaccion);
			
			//si el resultado de la busqueda es null salir y marcar error
			if( tr == null ) return new OperationRestResult(false, "error no se encontro la transaccion con id "+idtransaccion);
			
			//poner el campo correspondiente en null
			switch(tipoComprobante) {
				case "ticket": tr.setTicket(fileName); break;
				case "factura": tr.setFactura(fileName); break;
				case "complemento": tr.setComplemento(fileName); break;
				case "voucher": tr.setVoucher(fileName); break;
				case "transferencia": tr.setTransferencia(fileName); break;
				case "cheque": tr.setCheque(fileName); break;
				default:
					log.info("error no se encontro campo a asignar para "+tipoComprobante);
					return new OperationRestResult(false, "error "+tipoComprobante+" no encontrado");
			}
			
			//intentar guardar cambios
			transaccionHome.merge(tr);
			
			log.info("campo "+tipoComprobante+" exitosamente actualizado, transaccion "+idtransaccion);
			
			//regresar que la transaccion se hizo exitosamenre
			return new OperationRestResult(true,null);
			
		}catch( NoResultException ex ) {
			return new OperationRestResult(false, "transaccion con id "+idtransaccion+" no encontrada");
		} catch(Exception ex) {
			log.error("error al intentar actualizar campo "+ex.getMessage());
			return new OperationRestResult(false, "error al intentar actualizar campo "+ex.getMessage());
		}
	
	}
	
	@Override
	public OperationRestResult removeAttachment(int idtransaccion,String tipoComprobante) {
		
		String temp = null;
		
		log.info("intentando asignar a null el campo "+tipoComprobante+" de la transaccion "+idtransaccion);
		
		try {
			
			//intentar buscar transaccion
			Transaccion tr = transaccionHome.findById(idtransaccion);
			
			//si el resultado de la busqueda es null salir y marcar error
			if( tr == null ) return new OperationRestResult(false, "error no se encontro la transaccion con id "+idtransaccion);
			
			//guardar el valor del campo 
			switch(tipoComprobante) {
				case "ticket": temp = tr.getTicket(); break;
				case "factura": temp = tr.getFactura(); break;
				case "complemento": temp = tr.getComplemento(); break;
				case "voucher": temp = tr.getVoucher(); break;
				case "transferencia": temp = tr.getTransferencia(); break;
				case "cheque": temp = tr.getCheque(); break;
				default: return  new OperationRestResult(false, "error tipo de documento no encontrado "+tipoComprobante);
			}
			
			//si el campo ya esta en null
			if( temp == null ) 
				return new OperationRestResult(false, "no hay archivo asignado de tipo "+tipoComprobante+" a la transaccion "+idtransaccion);
			
			//poner el campo correspondiente en null
			switch(tipoComprobante) {
				case "ticket": tr.setTicket(null); break;
				case "factura": tr.setFactura(null); break;
				case "complemento": tr.setComplemento(null); break;
				case "voucher": tr.setVoucher(null); break;	
				case "transferencia": tr.setTransferencia(null); break;
				case "cheque": tr.setCheque(null); break;
			}
			
			//intentar guardar cambios
			transaccionHome.merge(tr);
			
			log.info("campo "+tipoComprobante+" exitosamente asignado a null de la transaccion "+idtransaccion);
			
			//regresar el nombre del archivo que se elimino en la variable message
			return new OperationRestResult(true,temp);
			
		} catch(NoResultException ex) {
			return new OperationRestResult(false, "error no se encontro la transaccion con id "+idtransaccion);
		} catch( Exception ex ) {
			return new OperationRestResult(false,"error al intentar eliminar documento "+ex.getMessage());
		}
		
	}
	
	public List<String> getAttachments(int idtransaccion) {
		
		List<String> list = new ArrayList<String>();
		
		try {
			//intentar buscar transaccion
			Transaccion tr = transaccionHome.findById(idtransaccion);
			
			if( tr.getTicket() != null ) list.add(tr.getTicket());
			if( tr.getFactura() != null ) list.add(tr.getFactura());
			if( tr.getComplemento() != null ) list.add(tr.getComplemento());
			if( tr.getVoucher() != null ) list.add(tr.getVoucher());
			if( tr.getTransferencia() != null ) list.add(tr.getTransferencia());
			if( tr.getCheque() != null) list.add(tr.getCheque());
			
			return list;
		}catch(Exception ex) {
			ex.printStackTrace();
			return list;
		}
		
	}
}
