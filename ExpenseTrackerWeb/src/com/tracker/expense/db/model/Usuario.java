package com.tracker.expense.db.model;
// Generated Oct 18, 2019, 8:12:21 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name="usuario"
    ,catalog="expensetrackerdb"
    , uniqueConstraints = @UniqueConstraint(columnNames="usuario") 
)
public class Usuario  implements java.io.Serializable {


     private Integer idusuario;
     private int version;
     private String usuario;
     private String clave;
     private int activo;
     private String token;
     private int rolnum;

    public Usuario() {
    }

	
    public Usuario(int activo, int rolnum) {
        this.activo = activo;
        this.rolnum = rolnum;
    }
    public Usuario(String usuario, String clave, int activo, String token, int rolnum) {
       this.usuario = usuario;
       this.clave = clave;
       this.activo = activo;
       this.token = token;
       this.rolnum = rolnum;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idusuario", unique=true, nullable=false)
    public Integer getIdusuario() {
        return this.idusuario;
    }
    
    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }
    @Version
    @Column(name="version", nullable=false)
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    @Column(name="usuario", unique=true, length=45)
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    @Column(name="clave", length=60)
    public String getClave() {
        return this.clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    @Column(name="activo", nullable=false)
    public int getActivo() {
        return this.activo;
    }
    
    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    @Column(name="token", length=200)
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    @Column(name="rolnum", nullable=false)
    public int getRolnum() {
        return this.rolnum;
    }
    
    public void setRolnum(int rolnum) {
        this.rolnum = rolnum;
    }




}


