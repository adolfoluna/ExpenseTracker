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

import com.tracker.expense.db.dto.CuentaResumenDto;
import com.tracker.expense.db.dto.home.CuentaResumenDtoHome;

@RequestScoped
@Path("/cuenta")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
@Secured({ Role.ADMIN, Role.OPERATOR, Role.READ_ONLY})
public class CuentaRestService {
	
	private static final Log log = LogFactory.getLog(CuentaRestService.class);
	
	@EJB private CuentaResumenDtoHome cuentaResumenHome;
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "HOLA MUNDO";
	}
	
	@GET
	@Path("/getresumen")
	@Produces(MediaType.APPLICATION_JSON)
	public CuentaResumenDto[] getResumen() {
		log.info("obteniendo resumen de cuentas...");
		return cuentaResumenHome.getResumen();
	}

}
