package com.tracker.expense.db.dto;

import java.io.Serializable;

public class CuentaResumenDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5462697323068896875L;
	
	private int idcuenta;
	private String nombre;
	private long saldo;
	private long saldoMonedaBase;
	private int idmoneda;
	private String moneda;
	private String tipo;
	private boolean monedaBase;
	private long semanaAnterior;
	private long semanaActual;
	private long mesAnterior;
	private long mesActual;
	
	public CuentaResumenDto() {
		
	}
	
	public CuentaResumenDto(int idcuenta, String nombre, long saldo, long saldoMonedaBase, int idmoneda, String moneda,
			String tipo,byte monedaBase, long semanaAnterior, long semanaActual, long mesAnterior, long mesActual) {
		super();
		this.idcuenta = idcuenta;
		this.nombre = nombre;
		this.saldo = saldo;
		this.saldoMonedaBase = saldoMonedaBase;
		this.idmoneda = idmoneda;
		this.moneda = moneda;
		this.tipo = tipo;
		
		if(monedaBase>0)
			this.setMonedaBase(true);
		else
			this.setMonedaBase(false);
		
		this.semanaAnterior = semanaAnterior;
		this.semanaActual = semanaActual;
		this.mesAnterior = mesAnterior;
		this.mesActual = mesActual;
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
	public long getSaldoMonedaBase() {
		return saldoMonedaBase;
	}
	
	public void setSaldoMonedaBase(long saldoMonedaBase) {
		this.saldoMonedaBase = saldoMonedaBase;
	}
	
	public int getIdmoneda() {
		return idmoneda;
	}
	
	public void setIdmoneda(int idmoneda) {
		this.idmoneda = idmoneda;
	}
	
	public String getMoneda() {
		return moneda;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isMonedaBase() {
		return monedaBase;
	}
	
	public void setMonedaBase(boolean monedaBase) {
		this.monedaBase = monedaBase;
	}
	
	public long getSemanaAnterior() {
		return semanaAnterior;
	}
	
	public void setSemanaAnterior(long semanaAnterior) {
		this.semanaAnterior = semanaAnterior;
	}
	
	public long getSemanaActual() {
		return semanaActual;
	}
	
	public void setSemanaActual(long semanaActual) {
		this.semanaActual = semanaActual;
	}
	
	public long getMesAnterior() {
		return mesAnterior;
	}
	
	public void setMesAnterior(long mesAnterior) {
		this.mesAnterior = mesAnterior;
	}
	
	public long getMesActual() {
		return mesActual;
	}
	
	public void setMesActual(long masActual) {
		this.mesActual = masActual;
	}

}
