package com.tracker.expense.db.dto;

import java.io.Serializable;

public class GrupoArticuloDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 77503671168189891L;
	
	private int idgrupo;
	private String nombregrupo;
	private int idarticulo;
	private String nombrearticulo;
	private int version;

	public GrupoArticuloDto() {
		
	}

	public GrupoArticuloDto(int idgrupo, String nombregrupo, int idarticulo, String nombrearticulo, int version) {
		this.idgrupo = idgrupo;
		this.nombregrupo = nombregrupo;
		this.idarticulo = idarticulo;
		this.nombrearticulo = nombrearticulo;
		this.version = version;
	}

	public int getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(int idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombregrupo() {
		return nombregrupo;
	}

	public void setNombregrupo(String nombregrupo) {
		this.nombregrupo = nombregrupo;
	}

	public int getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	public String getNombrearticulo() {
		return nombrearticulo;
	}

	public void setNombrearticulo(String nombrearticulo) {
		this.nombrearticulo = nombrearticulo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
