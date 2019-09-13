package com.tracker.expense.db.dto;

import java.io.Serializable;

public class ProveedorDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4212205532117578473L;

	private int idproveedor;
	private String nombre;
	private int version;
	
	public ProveedorDto() {
		
	}

	public ProveedorDto(int idproveedor, String nombre, int version) {
		super();
		this.idproveedor = idproveedor;
		this.nombre = nombre;
		this.version = version;
	}
	
	public int getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(int idproveedor) {
		this.idproveedor = idproveedor;
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
