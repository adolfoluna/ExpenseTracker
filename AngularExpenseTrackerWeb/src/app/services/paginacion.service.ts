import { Injectable } from '@angular/core';

import { Observable, Subject } from "rxjs";

import { PaginacionObject, PaginacionItem } from '../model/paginacion';

@Injectable({
  providedIn: 'root'
})
export class PaginacionService {
    
    numeroPaginasMostradas = 5;
    
    
    private subject = new Subject<any>();

    constructor() { }
    
    agregarPaginas(paginacion:PaginacionObject) {
        
        //calcula el numero total de paginas que hay
        this.calcularNumeroPaginas(paginacion);
        
        //eliminar paginas existentes
        paginacion.paginas = [];
        
        //calcular el limite inferior de numero de pagina a motrar
        var temp:number = Math.floor( paginacion.pagina / this.numeroPaginasMostradas );
        temp = temp * this.numeroPaginasMostradas;

        //repetir hasta llegar al numero de paginas a mostrar o hasta llegar al final
        for( var y = temp; y < (temp+this.numeroPaginasMostradas) && y < paginacion.numeroPaginas; y++ ) {
            paginacion.paginas.push(y+1);//numero de pagina a mostrar
        }
        
    }
    
    triggerCambioPagina() {
        this.subject.next({text:"asddsa"});
    }
    
    cambioPaginaEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
    agregarPaginasOld(paginacion:PaginacionObject) {
      
      //calcula el numero total de paginas que hay
      this.calcularNumeroPaginas(paginacion);
      
      //agregar el limite de resultados a la ruta
      var aux = paginacion.ruta + paginacion.limite + "/";
            
      //agregar ruta de pagina anterior
      this.calcularPaginaAnterior(paginacion);
      
      //agregar ruta de pagina siguiente
      this.calcularPaginaSiguiente(paginacion);
      
      //agregar la ruta de la primer pagina
      paginacion.rutaPrimero = aux +"0";
      
      //agregar la ruta de la ultima pagina
      this.calcularPaginaUltima(paginacion);
      
      //eliminar paginas existentes
      paginacion.paginas = [];
   
      //calcular el limite inferior de numero de pagina a motrar
      /*var temp:number = Math.floor( paginacion.pagina / this.numeroPaginasMostradas );
      temp = temp * this.numeroPaginasMostradas;

      //repetir hasta llegar al numero de paginas a mostrar o hasta llegar al final
      for( var y = temp; y < (temp+this.numeroPaginasMostradas) && y < paginacion.numeroPaginas; y++ ) {
         
          //crear el boton que se va a mostrar como pagina
          var item = new PaginacionItem(
                  y+1,//numero de pagina mostrada
                  aux+y,//ruta
                  false //
                  );
          
          //actualizar la bandera de activo en caso de que este link corresponda con la pagina actual
          if( y == paginacion.pagina )
              item.activo = true;
          
          paginacion.paginas.push(item);
      }*/
      
  }
  
  calcularNumeroPaginas(paginacion:PaginacionObject) {
      paginacion.numeroPaginas = paginacion.numResultados / paginacion.limite;
      paginacion.numeroPaginas = Math.floor(paginacion.numeroPaginas);
      var temp:number = paginacion.numResultados % paginacion.limite;
      if( temp > 0 ) paginacion.numeroPaginas++;
  }
  
  calcularPaginaAnterior(paginacion:PaginacionObject) {
      
      //si estamos en la pagina 1 entonces no existe la pagina anterior
      if( paginacion.pagina <= 0 ) {
          paginacion.rutaAnterior = null;
          return;
      }
      
      //pagina anterior es pagina actual - 1
      var x = paginacion.pagina -1;
      
      //ruta de pagina anterior
      paginacion.rutaAnterior = paginacion.ruta + paginacion.limite + "/" + x;
     
  }
  
  calcularPaginaSiguiente(paginacion:PaginacionObject) {
      
      //pagina siguiente es pagina actual + 1
      var x = paginacion.pagina + 1;
      
      //si ya no se puede navegar a una pagina siguiente, ponerla en null
      if( x >= paginacion.numeroPaginas ) {
          paginacion.rutaSiguiente = null;
          return;
      }
      
      //ruta de pagina siguiente
      paginacion.rutaSiguiente = paginacion.ruta + paginacion.limite + "/" + x;
     
  }
  
  calcularPaginaUltima(paginacion:PaginacionObject) {
      
      var x = paginacion.numeroPaginas -1;
      
      if( x <= 0) {
          paginacion.rutaUltimo = null;
          return;
      }
      
      paginacion.rutaUltimo = paginacion.ruta + paginacion.limite + "/" + x;
  }
}
