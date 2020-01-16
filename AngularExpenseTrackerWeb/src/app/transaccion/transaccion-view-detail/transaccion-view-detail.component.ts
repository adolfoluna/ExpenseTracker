import { Component, OnInit } from '@angular/core';

import { enviroment } from '../../model/enviroment';

import { ViewItemService } from "../../services/view.item.service";

import * as $ from "jquery";

@Component({
  selector: 'app-transaccion-view-detail',
  templateUrl: './transaccion-view-detail.component.html',
  styleUrls: ['./transaccion-view-detail.component.css']
})
export class TransaccionViewDetailComponent implements OnInit {
    
    transaction_view_row:any = {idtransaccion:0};

    constructor(private viewItemService:ViewItemService) { }

    ngOnInit() {
        //se ejecuta cada ves que se presiona el boton de ver transaccion
        this.viewItemService.viewItemEvent().subscribe(message=> this.viewTransactionEvent(message));
    }
  
    viewTransactionEvent(obj:any) {
        
        //activar el tab de ver el detalle de la transaccion y desactivar los otros
        $("#tabview").addClass("active");
        $("#tabedit").removeClass("active");
        $("#tablist").removeClass("active");
        
        
        //guardar la referencia del registro que se esta viendo
        this.transaction_view_row = obj;
        
        if( this.transaction_view_row.complementoRequerido)
            this.transaction_view_row.complementoRequeridoNombre = "Si";
        else
            this.transaction_view_row.complementoRequeridoNombre = "No";
        
  }
    
    clickViewDoc(type:string) {
        if(this.transaction_view_row[type] && this.transaction_view_row[type] != null )
          window.open(enviroment.docsURL+this.transaction_view_row[type]);
    }
    
    clickClose() {
        //activar el tab de ver el detalle de la transaccion y desactivar los otros
          $("#tablist").addClass("active");
          $("#tabview").removeClass("active");
          $("#tabedit").removeClass("active");
      }

}
