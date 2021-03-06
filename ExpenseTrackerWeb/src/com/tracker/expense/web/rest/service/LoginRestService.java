package com.tracker.expense.web.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tracker.expense.db.dto.UsuarioDto;
import com.tracker.expense.db.home.RolUsuarioHome;
import com.tracker.expense.db.home.UsuarioHome2;

@RequestScoped
@Path("/login")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class LoginRestService {
	
	@EJB private UsuarioHome2 usuarioHome;
	
	public LoginRestService() {
		
	}
	
	@GET
	@Path("/authenticate/{user}/{pwd}")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult authenticate( @PathParam("user") String user, @PathParam("pwd") String pwd) {
		
		//intentar hacer login, en caso de ser exitoso regresa el token creado
		UsuarioDto udto = usuarioHome.userLogin(user, pwd);
		
		//no se pudo iniciar sesion, regresar error
		if( udto == null )
			return new OperationRestResult(false, "Error usuario o contrasena incorrecto no encontrado");
		
		//regresar inf del usuario, token, usuario y rol del usuario
		return new OperationRestResult(udto);
	}
	
	@GET
	@Path("/logout/{user}")
	public OperationRestResult logout(@PathParam("user") String user) {
		
		//borrar todos los usuarios y rutas, para provocar que se vuelvan a consultar
		RolUsuarioHome.clear();
		
		return new OperationRestResult(usuarioHome.logout(user), null);
	}
	
}
