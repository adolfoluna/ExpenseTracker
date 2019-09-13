package com.tracker.expense.web.rest.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@Provider
//@PreMatching
public class ResponseRestFilter implements ContainerResponseFilter {

	//private static final Log log = LogFactory.getLog(ResponseRestFilter.class);
	
	@Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
        //log.info( "Executing REST response filter" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Origin", "*" );
        //responseCtx.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
        responseCtx.getHeaders().add("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        responseCtx.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
    }

	
}
