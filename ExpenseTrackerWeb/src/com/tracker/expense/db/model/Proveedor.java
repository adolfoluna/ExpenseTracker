package com.tracker.expense.db.model;
// Generated Oct 18, 2019, 8:12:21 PM by Hibernate Tools 3.2.2.GA


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
 * Proveedor generated by hbm2java
 */
@Entity
@Table(name="proveedor"
    ,catalog="expensetrackerdb"
    , uniqueConstraints = @UniqueConstraint(columnNames="nombre") 
)
public class Proveedor  implements java.io.Serializable {


     private Integer idproveedor;
     private int version;
     private String nombre;
     private Set<Transaccion> transaccions = new HashSet<Transaccion>(0);

    public Proveedor() {
    }

    public Proveedor(String nombre, Set<Transaccion> transaccions) {
       this.nombre = nombre;
       this.transaccions = transaccions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idproveedor", unique=true, nullable=false)
    public Integer getIdproveedor() {
        return this.idproveedor;
    }
    
    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }
    @Version
    @Column(name="version", nullable=false)
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    @Column(name="nombre", unique=true, length=45)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="proveedor")
    public Set<Transaccion> getTransaccions() {
        return this.transaccions;
    }
    
    public void setTransaccions(Set<Transaccion> transaccions) {
        this.transaccions = transaccions;
    }




}


