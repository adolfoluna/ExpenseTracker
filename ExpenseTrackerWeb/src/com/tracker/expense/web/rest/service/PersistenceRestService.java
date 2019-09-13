package com.tracker.expense.web.rest.service;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.AdvancedSearchDtoField;
import com.tracker.expense.db.dto.AdvancedSearchDtoGroup;
import com.tracker.expense.db.dto.ArticuloDto;
import com.tracker.expense.db.dto.CategoriaDto;
import com.tracker.expense.db.dto.CuentaDto;
import com.tracker.expense.db.dto.GrupoArticuloDto;
import com.tracker.expense.db.dto.GrupoDto;
import com.tracker.expense.db.dto.MonedaDto;
import com.tracker.expense.db.dto.ProveedorDto;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.dto.TipoCambioDto;
import com.tracker.expense.db.dto.TransaccionArticuloDto;
import com.tracker.expense.db.dto.TransaccionDto;
import com.tracker.expense.db.dto.home.PersistenceDtoRemote;
import com.tracker.expense.db.dto.home.TransaccionAttachmentRemote;
import com.tracker.expense.db.dto.home.TransaccionDescripcionUpdateRemote;
import com.tracker.expense.web.util.AddRemoveFileUtil;

@RequestScoped
@Path("/persistence")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class PersistenceRestService {
	
	private static final Log log = LogFactory.getLog(PersistenceRestService.class);
	
	@EJB(beanName = "CuentaDtoHome") private PersistenceDtoRemote cuentaDtoHome;
	@EJB(beanName = "MonedaDtoHome") private PersistenceDtoRemote monedaDtoHome;
	@EJB(beanName = "TipoCambioDtoHome") private PersistenceDtoRemote tipoCambioDtoHome;
	@EJB(beanName = "ProveedorDtoHome") private PersistenceDtoRemote proveedorDtoHome;
	@EJB(beanName = "TransaccionDtoHome") private PersistenceDtoRemote transaccionDtoHome;
	@EJB(beanName = "TransaccionArticuloDtoHome") private PersistenceDtoRemote transaccionArticuloDtoHome;
	@EJB(beanName = "CategoriaDtoHome") private PersistenceDtoRemote categoriaDtoHome;
	@EJB(beanName = "ArticuloDtoHome") private PersistenceDtoRemote articuloDtoHome;
	@EJB(beanName = "GrupoDtoHome") private PersistenceDtoRemote grupoDtoHome;
	@EJB(beanName = "GrupoArticuloDtoHome") private PersistenceDtoRemote grupoArticuloDtoHome;
	@EJB private TransaccionDescripcionUpdateRemote transaccionDescripcionHome;
	@EJB private TransaccionAttachmentRemote transaccionAttachmentRemote;
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "HOLA MUNDO";
	}
	
	@POST
	@Path("/remover")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult remove(SearchDto obj,@Context ServletContext servletContext) {
		
		if( obj.getCatalogo() == null )
			return new OperationRestResult(false, "error, catalogo en nulo");
		
		PersistenceDtoRemote persistence = getEjbHome(obj.getCatalogo());
		
		if( persistence == null ) 
			return new OperationRestResult(false, "error, catalogo "+obj.getCatalogo()+" no encontrado");
		
		//si se va a remover una transaccion entonces tambien eliminar todos los archivos guardados de esta transaccion
		if( obj.getCatalogo().equals("transaccion") && !eliminarArchivos(obj,servletContext) ) 
				return new OperationRestResult(false, "error no se pudieron eliminar todos los archivos asociados a la transaccion");

		//obtener lista y regresar el resultado
		OperationRestResult res = persistence.removeDto(obj);
		
		if( obj.getCatalogo().equals("transaccionarticulo") && res.isSuccess() )
			transaccionDescripcionHome.updateDescription(Integer.parseInt(obj.getSearhFieldsMap().get("idtransaccion")));
		
		return res;
	}
	
	@POST
	@Path("/listar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getList(SearchDto obj) {
		
		if( obj.getCatalogo() == null )
			return new OperationRestResult(false, "error, catalogo en nulo");
		
		String filtro = "";
		
		if( obj.getAdvancedSearchGroups() != null ) {
			
			for( AdvancedSearchDtoGroup grupo : obj.getAdvancedSearchGroups() ) {
				
				if( grupo.getUnion() != null )
					filtro+=" "+grupo.getUnion();
				
				filtro+=" (";
				
				for( AdvancedSearchDtoField field : grupo.getFields() ) {
					
					if( field.getUnion() != null )
						filtro+=field.getUnion()+" ";
					
					filtro+=field.getFieldName()+field.getComparator()+field.getFieldValue();
				}
				
				filtro+=")";
					
			}
		}
		
		log.info("listando catalogo:"+obj.getCatalogo()+" limite:"+obj.getLimite()+" pagina:"+obj.getPagina()+" filtro:"+filtro.trim());
		
		PersistenceDtoRemote persistence = getEjbHome(obj.getCatalogo());
		
		if( persistence == null ) 
			return new OperationRestResult(false, "error, catalogo "+obj.getCatalogo()+" no encontrado");
		
		//obtener lista y regresar el resultado
		return persistence.listDto(obj);
	}
	
	@POST
	@Path("/obtener")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getDto(SearchDto obj) {
		
		if( obj.getCatalogo() == null )
			return new OperationRestResult(false, "error, catalogo en nulo");
		
		//obtener el ejb que va a hacer el query
		PersistenceDtoRemote persistence = getEjbHome(obj.getCatalogo());
		
		//si no se asigno un ejb entonces salir de la rutina
		if( persistence == null ) 
			return new OperationRestResult(false, "error, catalogo "+obj.getCatalogo()+" no encontrado");
		
		//obtener objeto y regresarlo en el servicio
		return persistence.getDto(obj);
	}
	
	@POST
	@Path("/guardar/cuenta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(CuentaDto cuenta) {
		return upsertDto(cuenta, cuentaDtoHome);
	}
	
	@POST
	@Path("/guardar/moneda")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(MonedaDto cuenta) {
		return upsertDto(cuenta, monedaDtoHome);
	}
	
	@POST
	@Path("/guardar/tipocambio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(TipoCambioDto tc) {
		return upsertDto(tc, tipoCambioDtoHome);
	}
	
	@POST
	@Path("/guardar/proveedor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(ProveedorDto tc) {
		return upsertDto(tc, proveedorDtoHome);
	}
	
	@POST
	@Path("/guardar/transaccion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(TransaccionDto tc) {
		//convertir de fecha en string a objeto Date
		tc.setFecha(tc.stringToDate(tc.getFechaString()));
		return upsertDto(tc, transaccionDtoHome);
	}
	
	@POST
	@Path("/guardar/transaccionarticulo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(TransaccionArticuloDto tc) {
		OperationRestResult res = upsertDto(tc, transaccionArticuloDtoHome);
		
		//actualizar la descripcion de la transaccion en caso de que haya sido exitosa la operacion
		if( res.isSuccess() )
			transaccionDescripcionHome.updateDescription(tc.getIdtransaccion());
		
		return res;
	}
	
	@POST
	@Path("/guardar/categoria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(CategoriaDto categoria) {
		return upsertDto(categoria, categoriaDtoHome);
	}
	
	@POST
	@Path("/guardar/articulo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(ArticuloDto categoria) {
		return upsertDto(categoria, articuloDtoHome);
	}
	
	@POST
	@Path("/guardar/grupo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(GrupoDto grupo) {
		return upsertDto(grupo, grupoDtoHome);
	}
	
	@POST
	@Path("/guardar/grupoarticulo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upsertDto(GrupoArticuloDto grupoArticulo) {
		return upsertDto(grupoArticulo, grupoArticuloDtoHome);
	}
	
	
	private OperationRestResult upsertDto(Object ob,PersistenceDtoRemote ejb) {
		return ejb.upsertDto(ob);
	}
	
	
	private PersistenceDtoRemote getEjbHome(String catalogo) {
		
		switch(catalogo) {
			case "categoria": return categoriaDtoHome;
			case "cuenta": return cuentaDtoHome;
			case "moneda": return monedaDtoHome;
			case "tipocambio": return tipoCambioDtoHome;
			case "proveedor": return proveedorDtoHome;
			case "transaccion": return transaccionDtoHome;
			case "transaccionarticulo": return transaccionArticuloDtoHome;
			case "articulo": return articuloDtoHome;
			case "grupo": return grupoDtoHome;
			case "grupoarticulo": return grupoArticuloDtoHome;
		}
		
		return null;
	}
	
	private boolean eliminarArchivos(SearchDto dto, ServletContext servletContext) {
		
		HashMap<String, String> temp = dto.getSearhFieldsMap();
		
		if( temp == null )
			return false;
		
		if( !temp.containsKey("idtransaccion") ) 
			return false;
	
		List<String> lista = transaccionAttachmentRemote.getAttachments(Integer.valueOf(temp.get("idtransaccion")));
		
		for( String aux : lista ) {

			if( !AddRemoveFileUtil.removeFile(servletContext.getContextPath(), aux) )
				return false;
		}
		
		return true;
	}

}
