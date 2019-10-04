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
 * Articulo generated by hbm2java
 */
@Entity
@Table(name="articulo"
    ,catalog="expensetrackerdb"
)
public class Articulo  implements java.io.Serializable {


     private Integer idarticulo;
     private int version;
     private Categoria categoria;
     private String nombre;
     private Set<GrupoArticulo> grupoArticulos = new HashSet<GrupoArticulo>(0);
     private Set<TransaccionArticulo> transaccionArticulos = new HashSet<TransaccionArticulo>(0);

    public Articulo() {
    }

	
    public Articulo(Categoria categoria, String nombre) {
        this.categoria = categoria;
        this.nombre = nombre;
    }
    public Articulo(Categoria categoria, String nombre, Set<GrupoArticulo> grupoArticulos, Set<TransaccionArticulo> transaccionArticulos) {
       this.categoria = categoria;
       this.nombre = nombre;
       this.grupoArticulos = grupoArticulos;
       this.transaccionArticulos = transaccionArticulos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idarticulo", unique=true, nullable=false)
    public Integer getIdarticulo() {
        return this.idarticulo;
    }
    
    public void setIdarticulo(Integer idarticulo) {
        this.idarticulo = idarticulo;
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
    @JoinColumn(name="idcategoria", nullable=false)
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    @Column(name="nombre", nullable=false, length=50)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="articulo")
    public Set<GrupoArticulo> getGrupoArticulos() {
        return this.grupoArticulos;
    }
    
    public void setGrupoArticulos(Set<GrupoArticulo> grupoArticulos) {
        this.grupoArticulos = grupoArticulos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="articulo")
    public Set<TransaccionArticulo> getTransaccionArticulos() {
        return this.transaccionArticulos;
    }
    
    public void setTransaccionArticulos(Set<TransaccionArticulo> transaccionArticulos) {
        this.transaccionArticulos = transaccionArticulos;
    }




}


