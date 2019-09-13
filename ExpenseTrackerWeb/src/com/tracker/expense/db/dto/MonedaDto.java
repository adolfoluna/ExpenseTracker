package com.tracker.expense.db.dto;

import java.io.Serializable;

public class MonedaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6072529360137230580L;
	
	private int idmoneda;
	private String nombre;
	private boolean monedaBase;
	private int version;
	
	public MonedaDto() {
		
	}
	
	public MonedaDto(int idmoneda, String nombre, byte monedaBase, int version) {
		super();
		this.idmoneda = idmoneda;
		this.nombre = nombre;
		if( monedaBase > 0 )
			this.monedaBase = true;
		else
			this.monedaBase = false;
		this.version = version;
	}

	public int getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(int idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isMonedaBase() {
		return monedaBase;
	}

	public void setMonedaBase(boolean monedaBase) {
		this.monedaBase = monedaBase;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
