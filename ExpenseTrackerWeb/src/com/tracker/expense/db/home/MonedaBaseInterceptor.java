package com.tracker.expense.db.home;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


public class MonedaBaseInterceptor {
	
	@EJB private MonedaBaseHome monedaBaseHome;
	
	//private static final Log log = LogFactory.getLog(MonedaBaseInterceptor.class);
	
	 @AroundInvoke
	 public Object intercept(InvocationContext context) throws Exception {
	 
		 //si la moneda base ya fue determinada entonces proceguir y abandonar rutina
		 if( MonedaBaseHome.getMonedaBase() > 0 ) 
			 return context.proceed();
		 
		 //consultar la base de datos para poner la base moneda del sistema
		 monedaBaseHome.setMonedaBase();
		 
		 if( MonedaBaseHome.getMonedaBase() <= 0 )
			 return null;

		 return context.proceed();
	 }

}
