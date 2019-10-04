package com.tracker.expense.db.model;
// Generated Oct 3, 2019, 3:42:19 PM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Cuenta generated by hbm2java
 */
@Entity
@Table(name="cuenta"
    ,catalog="expensetrackerdb"
)
public class Cuenta  implements java.io.Serializable {


     private Integer idcuenta;
     private int version;
     private Moneda moneda;
     private String nombre;
     private long saldo;
     private String tipo;
     private Set<Transaccion> transaccions = new HashSet<Transaccion>(0);

    public Cuenta() {
    }

	
    public Cuenta(Moneda moneda, long saldo, String tipo) {
        this.moneda = moneda;
        this.saldo = saldo;
        this.tipo = tipo;
    }
    public Cuenta(Moneda moneda, String nombre, long saldo, String tipo, Set<Transaccion> transaccions) {
       this.moneda = moneda;
       this.nombre = nombre;
       this.saldo = saldo;
       this.tipo = tipo;
       this.transaccions = transaccions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idcuenta", unique=true, nullable=false)
    public Integer getIdcuenta() {
        return this.idcuenta;
    }
    
    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
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
    @JoinColumn(name="idmoneda", nullable=false)
    public Moneda getMoneda() {
        return this.moneda;
    }
    
    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
    
    @Column(name="nombre", length=100)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name="saldo", nullable=false)
    public long getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
    
    @Column(name="tipo", nullable=false, length=45)
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="cuenta")
    public Set<Transaccion> getTransaccions() {
        return this.transaccions;
    }
    
    public void setTransaccions(Set<Transaccion> transaccions) {
        this.transaccions = transaccions;
    }




}


