import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { DatepickerOptions } from 'ng2-datepicker';

import * as esLocale from 'date-fns/locale/es';
import * as $ from 'jquery';

import { ParentEditComponent } from "../../catalogos/parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { ListaService } from '../../services/lista.service';

import { SearchObject } from '../../model/searchobject'

@Component({
  selector: 'app-transaccion-edit',
  templateUrl: './transaccion-edit.component.html',
  styleUrls: ['./transaccion-edit.component.css']
})
export class TransaccionEditComponent  extends ParentEditComponent implements OnInit {
    
    idtransaccion:number = 0;

    constructor(private persistenceService:PersistenceService,
                private edititemService:EditItemService,
                private paginacionService:PaginacionService,
                private listaService:ListaService,
                private route:ActivatedRoute) {
        
        super(paginacionService,persistenceService,route);
       
    }
    
    ngOnInit() {
      //se ejecuta cada ves que se presiona el boton de editar registro
      //actualizar la variable idtransaccion
      this.edititemService.editItemEvent().subscribe( message => { this.editRowEvent(message); });
    }
    
    editRowEvent(obj:any) {
        
      //guardar el id de la transaccion que se esta editando
        if( obj != null )
            this.idtransaccion = obj.idtransaccion;
        else
            this.idtransaccion = 0;
        
        //activar el primer tab de la edicion de la transaccion
        $("#navtabsDatosgenerales a").removeClass("active");
        $("#navtabsDatosgenerales a").first().addClass("active");
        
        //activar el primer contenido que es la edicion de datos generales de la transaccion
        $("#tabcontentDatosgenerales div").removeClass("active");
        $("#tabcontentDatosgenerales div").first().addClass("active");
        
        //primero habilitar todos los tabs en caso de estar deshabilitados
        $("#navtabsDatosgenerales a").removeClass("disabled");
        
        //en caso de ser una nueva transaccion entonces desahilitar todas las pestañas excepto la primera
        if( obj == null )
            $("#navtabsDatosgenerales a").each( function(index) {if(index>0) $(this).addClass("disabled");});
     
    }
    
    
}