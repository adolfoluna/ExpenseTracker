package com.tracker.expense.db.dto;

import java.io.Serializable;

public class TransaccionArticuloDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3179077141254255585L;

	private int idtransaccion;
	
	private int idarticulo;
	private String articuloNombre;
    
    private float cantidad;
    private long subtotal;
    private float iva;
    private long total;
    
    private int version;
    
    public TransaccionArticuloDto() {
    	
    }

	public TransaccionArticuloDto(int idtransaccion, int idarticulo, String articuloNombre, float cantidad,
			long subtotal, float iva, long total, int version) {
		super();
		this.idtransaccion = idtransaccion;
		this.idarticulo = idarticulo;
		this.articuloNombre = articuloNombre;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
		this.iva = iva;
		this.total = total;
		this.version = version;
	}

	public int getIdtransaccion() {
		return idtransaccion;
	}

	public void setIdtransaccion(int idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public int getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	public String getArticuloNombre() {
		return articuloNombre;
	}

	public void setArticuloNombre(String articuloNombre) {
		this.articuloNombre = articuloNombre;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public long getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(long subtotal) {
		this.subtotal = subtotal;
	}

	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
}
