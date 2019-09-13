import { Component, OnInit } from '@angular/core';

import { ViewItemService } from "../../services/view.item.service";

import { PersistenceService } from '../../services/persistence.service';

import { SearchObject } from '../../model/searchobject';

import * as $ from "jquery";

@Component({
  selector: 'app-transaccion-list-articulos',
  templateUrl: './transaccion-list-articulos.component.html',
  styleUrls: ['./transaccion-list-articulos.component.css']
})
export class TransaccionListArticulosComponent implements OnInit {

    listaArticulosReadOnly = true;
    
    loadingArticulos = false;
    
    listaArticulos = [
                      //{articuloNombre:"asad",cantidad:1,subtotal:123,iva:123,total:12345}
                      ];
    
    articulosTotal = 0;
    
    constructor(
              private persistenceService:PersistenceService,
              private viewitemservice:ViewItemService) { }

  ngOnInit() {
      this.viewitemservice.viewItemEvent().subscribe(message=> this.viewTransactionDetailEvent(message));
  }
  
  viewTransactionDetailEvent(obj:any) {
      
      //reiniciar el calculo de la suma de los totales de los articulos
      this.articulosTotal = 0;
      
      //borrar arreglo anterior
      this.listaArticulos = [];
      
      //actualizar variable para indicar que se esta cargando la informacion
      this.loadingArticulos = true;
      
      //consultar la lista de articulos que pertenecen a la trasaccion
      var search:SearchObject = new SearchObject();
      search.catalogo = "transaccionarticulo";
      search.addSearchField("idtransaccion", obj.idtransaccion+"");
      search.limite = search.pagina = 0;
      this.persistenceService.getList(search).subscribe( res => {this.listarArticulosResponse(res); } );
      
  }
  
  listarArticulosResponse(res) {
      
      //actualizar variable para indicar que se finalizo el cargar la info
      this.loadingArticulos = false;
      
      if( res == null || res == undefined ) {
          alert("error no hubo respuesta del servidor");
          return;
      }
      
      if( res.success == false ) {
          alert(res.message);
          return;
      }
     
      if( res.data )
          this.listaArticulos = res.data; 
      
      for( var i = 0; i < this.listaArticulos.length; i++ ) {
          this.articulosTotal+= this.listaArticulos[i].total;
      }
  }

}
