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
		
		UsuarioDto udto = usuarioHome.userLogin(user, pwd);
		
		//no se pudo iniciar sesion, regresar error
		if( udto == null )
			return new OperationRestResult(false, "Error usuario o contrasena incorrecto no encontrado");
		
		//regresar inf del usuario, token, usuario y rol del usuario
		return new OperationRestResult(udto);
	}
	
	@GET
	@Path("/authenticate_external/{user}/{token}") 
	public OperationRestResult savetoken(@PathParam("user") String user,@PathParam("token") String token) {
		
		UsuarioDto udto = usuarioHome.userExternalLogin(user, token);
		
		//no se pudo iniciar sesion, regresar error
		if( udto == null )
			return new OperationRestResult(false, "Error usuario o contrasena incorrecto no encontrado");
		
		//regresar inf del usuario, token, usuario y rol del usuario
		return new OperationRestResult(udto);
	}
	
	@GET
	@Path("/logout/{user}")
	public OperationRestResult logout(@PathParam("user") String user) {
		return new OperationRestResult(usuarioHome.logout(user), null);
	}
	
}
