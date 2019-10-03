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

import com.tracker.expense.db.home.RolUsuarioHome;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    
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

    private void checkPermissions(List<Role> allowedRoles,String token,String route) throws Exception {
        
    	//si el usuario tiene privilegios de admin entonces de
    	//if( allowedRoles.contains(Role.ADMIN) )
    	//	return;
    	
    	int rolnum = rolUsuarioHome.getRolUsuario(token);
    	
    	if( rolnum <= 0 ) 
    		throw new Exception();
    	
    	//si el usuario es administrador entonces salir, ya que tiene permiso para todo
    	if( rolnum == Role.ADMIN.getRoleNum() )
    		return;
    	
    	if( !rolUsuarioHome.isRoleAllowed(rolnum, route) )
    		throw new Exception();
    	
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
        
        try {
        	
            if (methodRoles.isEmpty())
                checkPermissions(classRoles,requestContext.getHeaderString("UserToken"),requestContext.getUriInfo().getPath());
            else 
            	checkPermissions(methodRoles,requestContext.getHeaderString("UserToken"),requestContext.getUriInfo().getPath());

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}
}