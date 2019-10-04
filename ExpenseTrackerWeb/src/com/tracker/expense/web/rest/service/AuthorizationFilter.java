package com.tracker.expense.web.rest.service;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.home.RolUsuarioHome;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    
    private static final Log log = LogFactory.getLog(AuthorizationFilter.class);
    
    @EJB private RolUsuarioHome rolUsuarioHome;

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) { 
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private boolean checkPermissions(List<Role> allowedRoles,String username,String token,String route) {
        
    	//el usuario no esta firmado arrojar excepcion para provocar 
    	if( token == null ) {
    		log.info("usuario:"+username+" ruta:"+route+" token:"+token+" rol:na permitido:false");
    		return false;
    	}
    	
    	int rolnum = rolUsuarioHome.getRolUsuario(username,token);
    	
    	if( rolnum <= 0 ) {
    		log.info("usuario:"+username+" ruta:"+route+" token:"+token+" rol:"+rolnum+" permitido:false");
    		return false;
    	}
    	
    	//si el usuario es administrador entonces salir, ya que tiene permiso para todo
    	if( rolnum == Role.ADMIN.getRoleNum() ) {
    		log.info("usuario:"+username+" ruta:"+route+" token:"+token+" rol:"+rolnum+" permitido:true (admin)");
    		return true;
    	}
    	
    	if( rolUsuarioHome.isRoleAllowed(rolnum, route) ) {
    		log.info("usuario:"+username+" ruta:"+route+" token:"+token+" rol:"+rolnum+" permitido:true");
    		return true;
    	}
    	
    	log.info("usuario:"+username+" ruta:"+route+" token:"+token+" rol:"+rolnum+" permitido:false");
    	return false;
    	
    }

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		// Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
        
        String path = requestContext.getUriInfo().getPath();
        
        //remover los parametros del url en caso de que existan, con excepcion de /persistence
        if( requestContext.getUriInfo().getPathParameters().size() > 0 && !path.startsWith("/persistence/") ) {
        	
        	path = "";
        	
        	//longitud del path sin parametros es el total de segmentos menos el total de parametros en el url
        	int i = requestContext.getUriInfo().getPathSegments().size() - requestContext.getUriInfo().getPathParameters().size();
        	
        	//armar el path hasta antes de los parametros
        	for(int x = 0; x < i; x++) 
        		path+="/"+requestContext.getUriInfo().getPathSegments().get(x);
        	
        }
        
        //verificar los permisos del usuario segun las anotaciones que tiene la clase
        if( methodRoles.isEmpty() && !checkPermissions(classRoles,requestContext.getHeaderString("UserName"),requestContext.getHeaderString("UserToken"),path) ) 
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        
        //verificar los permisos del usuario segun las anotaciones que tiene el metodo de la clase
        if( !methodRoles.isEmpty() && !checkPermissions(methodRoles,requestContext.getHeaderString("UserName"),requestContext.getHeaderString("UserToken"),path) ) 
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
       
	}
}