package com.tracker.expense.db.model;
// Generated Oct 18, 2019, 8:12:21 PM by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TransaccionArticuloId generated by hbm2java
 */
@Embeddable
public class TransaccionArticuloId  implements java.io.Serializable {


     private int idtransaccion;
     private int idarticulo;

    public TransaccionArticuloId() {
    }

    public TransaccionArticuloId(int idtransaccion, int idarticulo) {
       this.idtransaccion = idtransaccion;
       this.idarticulo = idarticulo;
    }
   

    @Column(name="idtransaccion", nullable=false)
    public int getIdtransaccion() {
        return this.idtransaccion;
    }
    
    public void setIdtransaccion(int idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    @Column(name="idarticulo", nullable=false)
    public int getIdarticulo() {
        return this.idarticulo;
    }
    
    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TransaccionArticuloId) ) return false;
		 TransaccionArticuloId castOther = ( TransaccionArticuloId ) other; 
         
		 return (this.getIdtransaccion()==castOther.getIdtransaccion())
 && (this.getIdarticulo()==castOther.getIdarticulo());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdtransaccion();
         result = 37 * result + this.getIdarticulo();
         return result;
   }   


}


