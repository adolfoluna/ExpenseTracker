package com.tracker.expense.db.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaccionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3558721769145119782L;

	private int idtransaccion;
	private int idcuenta;
	private String nombrecuenta;
	private int idproveedor;
	private String nombreproveedor;
	private Date fecha;
	private String fechaString;
	private String articulos;
	private long total;
	private double tipocambio;
	private long totalbase;
    private String ticket;
    private String pago;
    private String factura;
    private String complemento;
    private boolean complementoRequerido;
    private String voucher;
    private String transferencia;
    private String cheque;
    private String nota;
    private int version;
    
    public TransaccionDto() {
    	
    }

	public TransaccionDto(int idtransaccion, int idcuenta, String nombrecuenta, int idproveedor, String nombreproveedor,
			Date fecha,String articulos, long total, double tipocambio,long totalbase, String ticket, String pago, String factura,
			String complemento,byte complementoRequerido, String voucher,String transferencia,String cheque, String nota,int version) {
		super();
		this.idtransaccion = idtransaccion;
		this.idcuenta = idcuenta;
		this.nombrecuenta = nombrecuenta;
		this.idproveedor = idproveedor;
		this.nombreproveedor = nombreproveedor;
		this.fecha = fecha;
		this.fechaString = dateToString(fecha);
		
		if( complementoRequerido > 0 )
			this.setComplementoRequerido(true);
		else
			this.setComplementoRequerido(false);
		
		this.articulos = articulos;
		this.total = total;
		this.tipocambio = tipocambio;
		this.totalbase = totalbase;
		this.ticket = ticket;
		this.pago = pago;
		this.factura = factura;
		this.complemento = complemento;
		this.voucher = voucher;
		this.transferencia = transferencia;
		this.cheque = cheque;
		this.nota = nota;
		this.version = version;
	}

	public int getIdtransaccion() {
		return idtransaccion;
	}

	public void setIdtransaccion(int idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public int getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(int idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getNombrecuenta() {
		return nombrecuenta;
	}

	public void setNombrecuenta(String nombrecuenta) {
		this.nombrecuenta = nombrecuenta;
	}

	public int getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(int idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getNombreproveedor() {
		return nombreproveedor;
	}

	public void setNombreproveedor(String nombreproveedor) {
		this.nombreproveedor = nombreproveedor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
		this.fechaString = dateToString(fecha);
	}

	public String getFechaString() {
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}
	
	public String getArticulos() {
		return articulos;
	}

	public void setArticulos(String articulos) {
		this.articulos = articulos;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public double getTipocambio() {
		return tipocambio;
	}

	public void setTipocambio(double tipocambio) {
		this.tipocambio = tipocambio;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
    private String dateToString(Date date) {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		return dateFormat.format(date);
    }
    
    public Date stringToDate(String dateString) {
    	try {
    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		return dateFormat.parse(dateString);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
    	
    }

	public boolean isComplementoRequerido() {
		return complementoRequerido;
	}

	public void setComplementoRequerido(boolean complementoRequerido) {
		this.complementoRequerido = complementoRequerido;
	}

	public long getTotalbase() {
		return totalbase;
	}

	public void setTotalbase(long totalbase) {
		this.totalbase = totalbase;
	}

	public String getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(String transferencia) {
		this.transferencia = transferencia;
	}

	public String getCheque() {
		return cheque;
	}

	public void setCheque(String cheque) {
		this.cheque = cheque;
	}
	
}
