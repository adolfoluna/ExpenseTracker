package com.tracker.expense.db.dto;

import java.io.Serializable;

public class TipoCambioDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470828965522303869L;

	private int idmonedaOrigen;
	private String nombreMonedaOrigen;
	private int idmonedaDestino;
	private String nombreMonedaDestino;
	private double tipocambio;
	private int version;
	
	public TipoCambioDto() {
		
	}
	
	//public TipoCambioDto(int idtipocambio, int idmonedaOrigen, String nombreMonedaOrigen, int idmonedaDestino,
	public TipoCambioDto(int idmonedaOrigen, String nombreMonedaOrigen, int idmonedaDestino,
			String nombreMonedaDestino, double tipocambio, int version) {
		super();
		//this.idtipocambio = idtipocambio;
		this.idmonedaOrigen = idmonedaOrigen;
		this.nombreMonedaOrigen = nombreMonedaOrigen;
		this.idmonedaDestino = idmonedaDestino;
		this.nombreMonedaDestino = nombreMonedaDestino;
		this.tipocambio = tipocambio;
		this.version = version;
	}

	public int getIdmonedaOrigen() {
		return idmonedaOrigen;
	}

	public void setIdmonedaOrigen(int idmonedaOrigen) {
		this.idmonedaOrigen = idmonedaOrigen;
	}

	public String getNombreMonedaOrigen() {
		return nombreMonedaOrigen;
	}

	public void setNombreMonedaOrigen(String nombreMonedaOrigen) {
		this.nombreMonedaOrigen = nombreMonedaOrigen;
	}

	public int getIdmonedaDestino() {
		return idmonedaDestino;
	}

	public void setIdmonedaDestino(int idmonedaDestino) {
		this.idmonedaDestino = idmonedaDestino;
	}

	public String getNombreMonedaDestino() {
		return nombreMonedaDestino;
	}

	public void setNombreMonedaDestino(String nombreMonedaDestino) {
		this.nombreMonedaDestino = nombreMonedaDestino;
	}

	public double getTipocambio() {
		return tipocambio;
	}

	public void setTipocambio(double tipocambio) {
		this.tipocambio = tipocambio;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
