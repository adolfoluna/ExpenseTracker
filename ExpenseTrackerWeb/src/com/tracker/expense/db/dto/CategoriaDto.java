package com.tracker.expense.db.dto;

import java.io.Serializable;

public class CategoriaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8304813089684743145L;
	private int idcategoria;
	private String nombre;
	private int version;
	
	public CategoriaDto() {
		
	}

	public CategoriaDto(int idcategoria, String nombre, int version) {
		super();
		this.idcategoria = idcategoria;
		this.nombre = nombre;
		this.version = version;
	}

	public int getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
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
