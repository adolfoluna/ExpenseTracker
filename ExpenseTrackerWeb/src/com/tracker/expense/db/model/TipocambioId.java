package com.tracker.expense.db.model;
// Generated Aug 10, 2019, 12:46:39 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TipocambioId generated by hbm2java
 */
@Embeddable
public class TipocambioId  implements java.io.Serializable {


     private int idmonedaOrigen;
     private int idmonedaDestino;

    public TipocambioId() {
    }

    public TipocambioId(int idmonedaOrigen, int idmonedaDestino) {
       this.idmonedaOrigen = idmonedaOrigen;
       this.idmonedaDestino = idmonedaDestino;
    }
   

    @Column(name="idmoneda_origen", nullable=false)
    public int getIdmonedaOrigen() {
        return this.idmonedaOrigen;
    }
    
    public void setIdmonedaOrigen(int idmonedaOrigen) {
        this.idmonedaOrigen = idmonedaOrigen;
    }

    @Column(name="idmoneda_destino", nullable=false)
    public int getIdmonedaDestino() {
        return this.idmonedaDestino;
    }
    
    public void setIdmonedaDestino(int idmonedaDestino) {
        this.idmonedaDestino = idmonedaDestino;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TipocambioId) ) return false;
		 TipocambioId castOther = ( TipocambioId ) other; 
         
		 return (this.getIdmonedaOrigen()==castOther.getIdmonedaOrigen())
 && (this.getIdmonedaDestino()==castOther.getIdmonedaDestino());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdmonedaOrigen();
         result = 37 * result + this.getIdmonedaDestino();
         return result;
   }   


}


