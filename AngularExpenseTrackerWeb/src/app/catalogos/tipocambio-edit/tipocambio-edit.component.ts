import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

import {SearchObject, SearchField} from '../../model/searchobject';
import {ParentEditComponent } from '../parent-edit.component';

@Component({
  selector: 'app-tipocambio-edit',
  templateUrl: './tipocambio-edit.component.html',
  styleUrls: ['./tipocambio-edit.component.css']
})
export class TipocambioEditComponent extends ParentEditComponent implements OnInit {

    datosForma = this.fb.group({
        tipocambio : [0],
        monedaOrigenSeleccionada : [],
        monedaDestinoSeleccionada: [],
        version: [0],
    });
    
    monedas = [];

    constructor(private persistenceService:PersistenceService,
                private paginacionService:PaginacionService,
                private edititemService:EditItemService,
                private fb: FormBuilder,
                private route: ActivatedRoute,) {
        super(paginacionService,persistenceService,route);
    }
    
    ngOnInit() {
 
        this.getMonedas();
        
        this.updateValues("tipocambio",["idmonedaorigen","idmonedadestino"], this.datosForma, this.formToInstance,this.instanceToForm);

        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => {
                if( message != null ) {
                    message.idmonedaorigen = message.idmonedaOrigen;
                    message.idmonedadestino = message.idmonedaDestino;
                }
                this.getInstanceFromServer("tipocambio",message);
                
        });
        
    }
    
    instanceToForm(obj:any) {
        
        if( obj == null ) {
            
            obj = { tipocambio:0, monedaOrigenSeleccionada:null, monedaDestinoSeleccionada:null, version:0 };
            
            this.datosForma.patchValue(obj);
            
            if(this.monedas.length > 0 ) {
                this.datosForma.controls.monedaOrigenSeleccionada.setValue(this.monedas[0]);
                this.datosForma.controls.monedaDestinoSeleccionada.setValue(this.monedas[0]);
            }
            
            return;
        }
        
        for( var i = 0; i < this.monedas.length; i++ ) {
            if( obj.idmonedaOrigen == this.monedas[i].idmoneda ) {
                this.datosForma.controls.monedaOrigenSeleccionada.setValue(this.monedas[i]);
                break;
            }
        }
        
        for( var i = 0; i < this.monedas.length; i++ ) {
            if( obj.idmonedaDestino == this.monedas[i].idmoneda ) {
                this.datosForma.controls.monedaDestinoSeleccionada.setValue(this.monedas[i]);
                break;
            }
        }
    }
    
    formToInstance() {
        //estos valores son usados en el metodo save
        this.datosForma.value.idmonedaOrigen = this.datosForma.controls.monedaOrigenSeleccionada.value.idmoneda;
        this.datosForma.value.idmonedaDestino = this.datosForma.controls.monedaDestinoSeleccionada.value.idmoneda;
        
        //estos valores son usados en el metodo remove
        this.datosForma.value.idmonedaorigen = this.datosForma.controls.monedaOrigenSeleccionada.value.idmoneda;
        this.datosForma.value.idmonedadestino = this.datosForma.controls.monedaDestinoSeleccionada.value.idmoneda
        
        return true;
    }
    
    getMonedas() {
        var search: SearchObject = new SearchObject();
        search.catalogo = "moneda"; //especificar que se consultara el calogo de moneda
        search.limite = 0;//limite en cero para que traiga toda la lista disponible
        search.searchFields = [  ];
        search.orderFields = [ "nombre"]; //ordenar la lista por nombre de moneda
        this.persistenceService.getList(search).subscribe( res => { this.monedas = res.data;});
    }

}
