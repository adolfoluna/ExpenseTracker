package com.tracker.expense.db.dto;

import java.io.Serializable;

public class ArticuloDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034537223099617466L;
	
	private int idarticulo;
	private int idcategoria;
	private String nombrecategoria;
	private String nombre;
	private int version;
	
	public ArticuloDto(int idarticulo, int idcategoria, String nombrecategoria, String nombre,int version) {
		super();
		this.idarticulo = idarticulo;
		this.idcategoria = idcategoria;
		this.nombrecategoria = nombrecategoria;
		this.nombre = nombre;
		this.setVersion(version);
	}
	
	public ArticuloDto() {
		
	}

	public int getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	public int getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getNombrecategoria() {
		return nombrecategoria;
	}

	public void setNombrecategoria(String nombrecategoria) {
		this.nombrecategoria = nombrecategoria;
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
