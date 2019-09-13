package com.tracker.expense.db.dto;

import java.io.Serializable;

public class GrupoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8876673085618426903L;
	
	private int idgrupo;
	private String nombre;
	private int version;
	
	public GrupoDto(int idgrupo, String nombre, int version) {
		this.idgrupo = idgrupo;
		this.nombre = nombre;
		this.version = version;
	}
	
	public GrupoDto() {
		
	}

	public int getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(int idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
	
}
