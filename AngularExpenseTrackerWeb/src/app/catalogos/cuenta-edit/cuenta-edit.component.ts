import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ParentEditComponent } from "../parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

import { SearchObject } from '../../model/searchobject';

@Component({
  selector: 'app-cuenta-edit',
  templateUrl: './cuenta-edit.component.html',
  styleUrls: ['./cuenta-edit.component.css']
})
export class CuentaEditComponent extends ParentEditComponent implements OnInit {
    
    datosForma = this.fb.group({
        idcuenta : [0],
        nombre : [""],
        saldo : [0],
        monedaSeleccionada : [],
        tipo: [""],
        version: [0],
    });
    
    monedas = [];

    constructor(private persistenceService:PersistenceService,
                private fb: FormBuilder,
                private edititemService:EditItemService,
                private paginacionService:PaginacionService,
                private route: ActivatedRoute,) {
        
        super(paginacionService,persistenceService,route);
       
    }
    
    ngOnInit() {
        
        this.getMonedas();
        
        this.updateValues("cuenta",["idcuenta"], this.datosForma, this.formToInstance,this.instanceToForm);

        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => {this.getInstanceFromServer("cuenta",message); });
    }
    
    instanceToForm(obj:any) {
        
        if( obj == null ) {
            obj = { idcuenta:0,nombre:"",saldo:0,monedaSeleccionada:null,tipo:"",version:0};
            this.datosForma.patchValue(obj);
            return;
        }
        
        for( var i = 0; i < this.monedas.length; i++ ) {
            if( obj.idmoneda == this.monedas[i].idmoneda ) {
                this.datosForma.controls.monedaSeleccionada.setValue(this.monedas[i]);
                break;
            }
        }
    }
    
    formToInstance() {
        
        if( this.datosForma.controls["nombre"].value.toString().trim().length <= 0 ) {
            alert("Nombre de cuenta en blanco");
            return;
        }
        this.datosForma.value.idmoneda = this.datosForma.controls.monedaSeleccionada.value.idmoneda;
        return true;
    }
    
    getMonedas() {
        var search: SearchObject = new SearchObject();
        search.catalogo = "moneda"; //especificar que se consultara el catalogo de moneda
        search.limite = 0;//limite en cero para que traiga toda la lista disponible
        search.searchFields = [];
        search.orderFields = [ "nombre"]; //ordenar la lista por nombre de moneda
        this.persistenceService.getList(search).subscribe( res => { this.monedasServiceResponse(res)});
    }
    
    monedasServiceResponse(res) {
        this.monedas = res.data;
        this.datosForma.controls.monedaSeleccionada.setValue(this.monedas[0]);    
    }

}

