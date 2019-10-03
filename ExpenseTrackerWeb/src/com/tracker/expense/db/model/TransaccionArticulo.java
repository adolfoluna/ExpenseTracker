package com.tracker.expense.db.model;
// Generated Oct 3, 2019, 1:28:57 AM by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * TransaccionArticulo generated by hbm2java
 */
@Entity
@Table(name="transaccion_articulo"
    ,catalog="expensetrackerdb"
)
public class TransaccionArticulo  implements java.io.Serializable {


     private TransaccionArticuloId id;
     private int version;
     private Transaccion transaccion;
     private Articulo articulo;
     private float cantidad;
     private long subtotal;
     private float iva;
     private long total;
     private long totalbase;

    public TransaccionArticulo() {
    }

    public TransaccionArticulo(TransaccionArticuloId id, Transaccion transaccion, Articulo articulo, float cantidad, long subtotal, float iva, long total, long totalbase) {
       this.id = id;
       this.transaccion = transaccion;
       this.articulo = articulo;
       this.cantidad = cantidad;
       this.subtotal = subtotal;
       this.iva = iva;
       this.total = total;
       this.totalbase = totalbase;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idtransaccion", column=@Column(name="idtransaccion", nullable=false) ), 
        @AttributeOverride(name="idarticulo", column=@Column(name="idarticulo", nullable=false) ) } )
    public TransaccionArticuloId getId() {
        return this.id;
    }
    
    public void setId(TransaccionArticuloId id) {
        this.id = id;
    }
    @Version
    @Column(name="version", nullable=false)
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idtransaccion", nullable=false, insertable=false, updatable=false)
    public Transaccion getTransaccion() {
        return this.transaccion;
    }
    
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idarticulo", nullable=false, insertable=false, updatable=false)
    public Articulo getArticulo() {
        return this.articulo;
    }
    
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    
    @Column(name="cantidad", nullable=false, precision=12, scale=0)
    public float getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    
    @Column(name="subtotal", nullable=false)
    public long getSubtotal() {
        return this.subtotal;
    }
    
    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }
    
    @Column(name="iva", nullable=false, precision=12, scale=0)
    public float getIva() {
        return this.iva;
    }
    
    public void setIva(float iva) {
        this.iva = iva;
    }
    
    @Column(name="total", nullable=false)
    public long getTotal() {
        return this.total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    @Column(name="totalbase", nullable=false)
    public long getTotalbase() {
        return this.totalbase;
    }
    
    public void setTotalbase(long totalbase) {
        this.totalbase = totalbase;
    }




}

