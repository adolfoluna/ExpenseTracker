package com.tracker.expense.db.dto;

import java.io.Serializable;

public class CuentaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048000078958797985L;
	
	private int idcuenta;
	private String nombre;
	private long saldo;
	private long saldoMonedaBase;
	private int idmoneda;
	private String moneda;
	private String tipo;
	private boolean monedaBase;
	private int version;
	
	public CuentaDto() {
		
	}

	public CuentaDto(int idcuenta, String nombre, long saldo,int idmoneda,String moneda,byte monedaBase,String tipo, int version) {
		super();
		this.idcuenta = idcuenta;
		this.nombre = nombre;
		this.saldo = saldo;
		this.setIdmoneda(idmoneda);
		this.moneda = moneda;
		this.setTipo(tipo);
		
		if(monedaBase>0)
			this.setMonedaBase(true);
		else
			this.setMonedaBase(false);
		
		this.version = version;
	}



	public int getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(int idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getSaldo() {
		return saldo;
	}

	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(int idmoneda) {
		this.idmoneda = idmoneda;
	}

	public long getSaldoMonedaBase() {
		return saldoMonedaBase;
	}

	public void setSaldoMonedaBase(long saldoMonedaBase) {
		this.saldoMonedaBase = saldoMonedaBase;
	}

	public boolean isMonedaBase() {
		return monedaBase;
	}

	public void setMonedaBase(boolean monedaBase) {
		this.monedaBase = monedaBase;
	}

}
