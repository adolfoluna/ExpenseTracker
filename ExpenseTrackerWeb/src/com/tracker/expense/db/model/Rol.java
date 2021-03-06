package com.tracker.expense.db.model;
// Generated Oct 18, 2019, 8:12:21 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name="rol"
    ,catalog="expensetrackerdb"
)
public class Rol  implements java.io.Serializable {


     private Integer idrol;
     private int version;
     private int rolnum;
     private String ruta;

    public Rol() {
    }

    public Rol(int rolnum, String ruta) {
       this.rolnum = rolnum;
       this.ruta = ruta;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idrol", unique=true, nullable=false)
    public Integer getIdrol() {
        return this.idrol;
    }
    
    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }
    @Version
    @Column(name="version", nullable=false)
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    @Column(name="rolnum", nullable=false)
    public int getRolnum() {
        return this.rolnum;
    }
    
    public void setRolnum(int rolnum) {
        this.rolnum = rolnum;
    }
    
    @Column(name="ruta", nullable=false, length=100)
    public String getRuta() {
        return this.ruta;
    }
    
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }




}


