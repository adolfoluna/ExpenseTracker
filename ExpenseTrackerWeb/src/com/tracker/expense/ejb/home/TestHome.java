package com.tracker.expense.ejb.home;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class TestHome
 */
@Stateless
@LocalBean
public class TestHome {

    /**
     * Default constructor. 
     */
    public TestHome() {
    	System.out.println("hola mundo constructor desde EJB");
    }

}
