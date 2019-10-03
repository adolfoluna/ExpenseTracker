package com.tracker.expense.db.dto;

import java.io.Serializable;
import java.util.Date;

public class RolDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4968995512787136760L;
	
	private int idrol;
	private int rolnum;
	private String ruta;
	private long expirationTime;
	private boolean permitido;
	private int version;

	public RolDto() {
		
	}

	public RolDto(int idrol, int rolnum, String ruta, int version) {
		super();
		this.idrol = idrol;
		this.rolnum = rolnum;
		this.ruta = ruta;
		this.version = version;
		this.expirationTime = new Date().getTime() + (15*60*1000);
		this.permitido = true;
	}

	public int getIdrol() {
		return idrol;
	}

	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}

	public int getRolnum() {
		return rolnum;
	}

	public void setRolnum(int rolnum) {
		this.rolnum = rolnum;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isPermitido() {
		return permitido;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}
	
	public boolean isExpired() {
		if( expirationTime > (new Date().getTime()))
			return false;
		else
			return true;
	}
	
}
