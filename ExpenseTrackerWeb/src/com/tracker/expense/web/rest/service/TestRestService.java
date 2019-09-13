package com.tracker.expense.web.rest.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class TestRestService {
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "HOLA MUNDO";
	}

}
