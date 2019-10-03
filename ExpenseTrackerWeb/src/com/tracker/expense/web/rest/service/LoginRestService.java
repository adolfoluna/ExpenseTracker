package com.tracker.expense.web.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
		
		String token = usuarioHome.genetateToken(user, pwd);
		
		if( token == null )
			return new OperationRestResult(false, "error usuario no encontrado");
		
		return new OperationRestResult(token);
	}
	
	@GET
	@Path("/logout/{user}")
	public OperationRestResult logout(@PathParam("user") String user) {
		return new OperationRestResult(usuarioHome.logout(user), null);
	}
	
	@GET
	@Path("/savetoken/{user}/{token}") 
	public OperationRestResult savetoken(@PathParam("user") String user,@PathParam("token") String token) {
		return new OperationRestResult(usuarioHome.setToken(user, token),null);
	}
	
	@GET
	@Secured({Role.ADMIN,Role.OPERATOR})
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "service test\r\n";
	}

}
