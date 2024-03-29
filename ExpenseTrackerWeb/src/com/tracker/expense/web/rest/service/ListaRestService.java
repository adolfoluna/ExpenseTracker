package com.tracker.expense.web.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.ListaDto;
import com.tracker.expense.db.dto.home.ListaDtoHome;

@RequestScoped
@Path("/lista")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
@Secured({Role.ADMIN,Role.OPERATOR,Role.READ_ONLY,Role.ACCOUNTANT})
public class ListaRestService {
	
	@EJB private ListaDtoHome listaDtoHome;
	private static final Log log = LogFactory.getLog(ListaRestService.class);
	
	@GET
	@Path("/proveedores/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getProveedoresNombre() {
		try {
			ListaDto res[] = listaDtoHome.getProveedoresOrdenadosPorNombre();
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de proveedores");
			
			return new OperationRestResult(res);
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la lista de proveedores ordenados por nombre "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}
	
	@GET
	@Path("/articulos/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getArticulosNombre() {
		try {
			ListaDto res[] = listaDtoHome.getArticulosOrdenadosPorNombre();
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de articulos");
			
			return new OperationRestResult(res);
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la lista de articulos ordenados por nombre "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}
	
	@GET
	@Path("/cuentas/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getCuentasNombre() {
		try {
			ListaDto res[] = listaDtoHome.getCuentasOrdenadosPorNombre();
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de cuentas");
			
			return new OperationRestResult(res);
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la lista de cuentas ordenadas por nombre "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}
	
	@GET
	@Path("/categorias/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getCategoriasNombre() {
		try {
			ListaDto res[] = listaDtoHome.getCategoriasOrdenadosPorNombre();
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de categorias");
			
			return new OperationRestResult(res);
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la lista de categorias ordenadas por nombre "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}
	
	@GET
	@Path("/grupos/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getGruposNombre() {
		try {
			ListaDto res[] = listaDtoHome.getGruposPorNombre();
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de grupos");
			
			return new OperationRestResult(res);
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la lista de grupos ordenadas por nombre "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}

}
