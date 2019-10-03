package com.tracker.expense.db.model;
// Generated Oct 3, 2019, 1:28:57 AM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Moneda generated by hbm2java
 */
@Entity
@Table(name="moneda"
    ,catalog="expensetrackerdb"
    , uniqueConstraints = @UniqueConstraint(columnNames="nombre") 
)
public class Moneda  implements java.io.Serializable {


     private Integer idmoneda;
     private int version;
     private String nombre;
     private byte monedaBase;
     private Set<Tipocambio> tipocambiosForIdmonedaDestino = new HashSet<Tipocambio>(0);
     private Set<Tipocambio> tipocambiosForIdmonedaOrigen = new HashSet<Tipocambio>(0);
     private Set<Cuenta> cuentas = new HashSet<Cuenta>(0);

    public Moneda() {
    }

	
    public Moneda(String nombre, byte monedaBase) {
        this.nombre = nombre;
        this.monedaBase = monedaBase;
    }
    public Moneda(String nombre, byte monedaBase, Set<Tipocambio> tipocambiosForIdmonedaDestino, Set<Tipocambio> tipocambiosForIdmonedaOrigen, Set<Cuenta> cuentas) {
       this.nombre = nombre;
       this.monedaBase = monedaBase;
       this.tipocambiosForIdmonedaDestino = tipocambiosForIdmonedaDestino;
       this.tipocambiosForIdmonedaOrigen = tipocambiosForIdmonedaOrigen;
       this.cuentas = cuentas;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idmoneda", unique=true, nullable=false)
    public Integer getIdmoneda() {
        return this.idmoneda;
    }
    
    public void setIdmoneda(Integer idmoneda) {
        this.idmoneda = idmoneda;
    }
    @Version
    @Column(name="version", nullable=false)
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    @Column(name="nombre", unique=true, nullable=false, length=10)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name="moneda_base", nullable=false)
    public byte getMonedaBase() {
        return this.monedaBase;
    }
    
    public void setMonedaBase(byte monedaBase) {
        this.monedaBase = monedaBase;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="monedaByIdmonedaDestino")
    public Set<Tipocambio> getTipocambiosForIdmonedaDestino() {
        return this.tipocambiosForIdmonedaDestino;
    }
    
    public void setTipocambiosForIdmonedaDestino(Set<Tipocambio> tipocambiosForIdmonedaDestino) {
        this.tipocambiosForIdmonedaDestino = tipocambiosForIdmonedaDestino;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="monedaByIdmonedaOrigen")
    public Set<Tipocambio> getTipocambiosForIdmonedaOrigen() {
        return this.tipocambiosForIdmonedaOrigen;
    }
    
    public void setTipocambiosForIdmonedaOrigen(Set<Tipocambio> tipocambiosForIdmonedaOrigen) {
        this.tipocambiosForIdmonedaOrigen = tipocambiosForIdmonedaOrigen;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="moneda")
    public Set<Cuenta> getCuentas() {
        return this.cuentas;
    }
    
    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }




}

