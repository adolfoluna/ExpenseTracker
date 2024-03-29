package com.tracker.expense.db.model;
// Generated Oct 18, 2019, 8:12:21 PM by Hibernate Tools 3.2.2.GA


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
 * Tipocambio generated by hbm2java
 */
@Entity
@Table(name="tipocambio"
    ,catalog="expensetrackerdb"
)
public class Tipocambio  implements java.io.Serializable {


     private TipocambioId id;
     private int version;
     private Moneda monedaByIdmonedaDestino;
     private Moneda monedaByIdmonedaOrigen;
     private double tipocambio;

    public Tipocambio() {
    }

    public Tipocambio(TipocambioId id, Moneda monedaByIdmonedaDestino, Moneda monedaByIdmonedaOrigen, double tipocambio) {
       this.id = id;
       this.monedaByIdmonedaDestino = monedaByIdmonedaDestino;
       this.monedaByIdmonedaOrigen = monedaByIdmonedaOrigen;
       this.tipocambio = tipocambio;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idmonedaOrigen", column=@Column(name="idmoneda_origen", nullable=false) ), 
        @AttributeOverride(name="idmonedaDestino", column=@Column(name="idmoneda_destino", nullable=false) ) } )
    public TipocambioId getId() {
        return this.id;
    }
    
    public void setId(TipocambioId id) {
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
    @JoinColumn(name="idmoneda_destino", nullable=false, insertable=false, updatable=false)
    public Moneda getMonedaByIdmonedaDestino() {
        return this.monedaByIdmonedaDestino;
    }
    
    public void setMonedaByIdmonedaDestino(Moneda monedaByIdmonedaDestino) {
        this.monedaByIdmonedaDestino = monedaByIdmonedaDestino;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmoneda_origen", nullable=false, insertable=false, updatable=false)
    public Moneda getMonedaByIdmonedaOrigen() {
        return this.monedaByIdmonedaOrigen;
    }
    
    public void setMonedaByIdmonedaOrigen(Moneda monedaByIdmonedaOrigen) {
        this.monedaByIdmonedaOrigen = monedaByIdmonedaOrigen;
    }
    
    @Column(name="tipocambio", nullable=false, precision=22, scale=0)
    public double getTipocambio() {
        return this.tipocambio;
    }
    
    public void setTipocambio(double tipocambio) {
        this.tipocambio = tipocambio;
    }




}


