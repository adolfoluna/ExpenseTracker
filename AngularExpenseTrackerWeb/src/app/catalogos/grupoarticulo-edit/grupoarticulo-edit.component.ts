import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ParentEditComponent } from "../parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { ListaService } from '../../services/lista.service';

import { SearchObject } from '../../model/searchobject';

@Component({
  selector: 'app-grupoarticulo-edit',
  templateUrl: './grupoarticulo-edit.component.html',
  styleUrls: ['./grupoarticulo-edit.component.css']
})
export class GrupoarticuloEditComponent extends ParentEditComponent implements OnInit {
    
    listaGrupos = [];
    grupoSeleccionado = [];
    
    listaArticulos = [];
    articuloSeleccionado = [];
    
    dropdownSettings = {
            singleSelection: true,
            idField: 'id',
            textField: 'name',
            allowSearchFilter: true,
            closeDropDownOnSelection : true,
            clearSearchFilter : true,
        };
    
    datosForma = this.fb.group({
        idgrupo : [0],
        idarticulo : [0],
        version: [0],
    });
    
    constructor(private persistenceService:PersistenceService,
                private fb: FormBuilder,
                private edititemService:EditItemService,
                private paginacionService:PaginacionService,
                private listaService : ListaService,
                private route: ActivatedRoute,) {
        
        super(paginacionService,persistenceService,route);
       
    }
    
    ngOnInit() {
        
        this.updateValues("grupoarticulo",["idgrupo","idarticulo"], this.datosForma, this.formToInstance,this.instanceToForm);

        this.listaService.getGruposNombre().subscribe( res => this.listaGruposResponse(res));
        this.listaService.getArticulosNombre().subscribe( res => this.listaArticulosResponse(res));
        
        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => {this.getInstanceFromServer("grupoarticulo",message); });
    }
    
    instanceToForm(obj:any) {
        
        if( obj == null ) {
            obj = { idgrupo:0,idarticulo:0,version:0};
            this.datosForma.patchValue(obj);
            this.grupoSeleccionado = [];
            this.articuloSeleccionado = [];
            return;
        }
        
        for( var i = 0; i < this.listaGrupos.length; i++) {
            if( this.listaGrupos[i].id == obj.idgrupo ) {
                this.grupoSeleccionado = [this.listaGrupos[i]];
                break;
            }
        }
        
        for( var i = 0; i < this.listaArticulos.length; i++) {
            if( this.listaArticulos[i].id == obj.idgrupo ) {
                this.articuloSeleccionado = [this.listaArticulos[i]];
                break;
            }
        }
        
    }
    
    formToInstance() {
        
        if(this.grupoSeleccionado.length <= 0 ) {
            alert("debe seleccionar un grupo");
            return false;
        }
        
        if(this.articuloSeleccionado.length <= 0 ) {
            alert("debe seleccionar un articulo");
            return false;
        }
            
        this.datosForma.value.idgrupo = this.grupoSeleccionado[0].id;
        this.datosForma.value.idarticulo = this.articuloSeleccionado[0].id;
        
        return true;
    }
    
    listaGruposResponse(res:any) {
        if( res == null || res == undefined || res.success == false)
            return;
        this.listaGrupos = res.data;
    }
    
    listaArticulosResponse(res:any) {
        if( res == null || res == undefined || res.success == false)
            return;
        this.listaArticulos = res.data; 
    }
}