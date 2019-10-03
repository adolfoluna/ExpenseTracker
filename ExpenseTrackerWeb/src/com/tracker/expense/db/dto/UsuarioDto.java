package com.tracker.expense.db.dto;

import java.io.Serializable;
import java.util.Date;

public class UsuarioDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6816540843020025470L;
	private int idusuario;
	private String clave;
	private boolean admin;
	private boolean activo;
	private String token;
	private int rolnum;
	private long expirationTime;
	private int version;
	
	public UsuarioDto() {
		
	}
	
	public UsuarioDto(int idusuario, String clave, int admin, int activo, String token, int rolnum,
			int version) {
		super();
		this.idusuario = idusuario;
		this.clave = clave;
		
		if( admin > 0) this.admin = true;
		else this.admin = false;
		
		if( activo > 0 ) this.activo = true;
		
		else this.activo = false;
		this.token = token;
		this.rolnum = rolnum;
		this.version = version;
		
		//expirar dato en 15 minutos
		setExpirationTime(new Date().getTime() + (15*1000*60));
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRolnum() {
		return rolnum;
	}

	public void setRolnum(int rolnum) {
		this.rolnum = rolnum;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	
}
