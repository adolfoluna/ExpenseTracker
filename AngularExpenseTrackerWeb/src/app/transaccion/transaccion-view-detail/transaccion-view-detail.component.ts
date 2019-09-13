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
    minWidth = 800
    marginLeft = "auto";

    constructor(private viewItemService:ViewItemService) { }

    ngOnInit() {
        //se ejecuta cada ves que se presiona el boton de ver transaccion
        this.viewItemService.viewItemEvent().subscribe(message=> this.viewTransactionEvent(message));
    }
  
    viewTransactionEvent(obj:any) {
        
        //ajustar la ventana segun el tamaño de pantalla
        if( window.innerWidth <= 1200 ) {
            this.minWidth = window.innerWidth - 20;
            this.marginLeft = "10px";
        } else {
            this.marginLeft = "auto";
            this.minWidth = 800;
        }
        
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

}
