
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ParentEditComponent } from "../parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

import { SearchObject } from '../../model/searchobject';

@Component({
    selector: 'app-proveedor-edit',
    templateUrl: './proveedor-edit.component.html',
    styleUrls: ['./proveedor-edit.component.css']
  })
export class ProveedorEditComponent extends ParentEditComponent implements OnInit {
    
    datosForma = this.fb.group({
        idproveedor : [0],
        nombre : [""],
        version: [0],
    });

    constructor(private persistenceService:PersistenceService,
                private fb: FormBuilder,
                private edititemService:EditItemService,
                private paginacionService:PaginacionService,
                private route: ActivatedRoute,) {
        
        super(paginacionService,persistenceService,route);
       
    }
    
    ngOnInit() {

        this.updateValues("proveedor",["idproveedor"], this.datosForma, this.formToInstance,this.instanceToForm);

        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => {this.getInstanceFromServer("proveedor",message); });
    }
    
    instanceToForm(obj:any) {
        
        if( obj == null ) {
            obj = { idproveedor:0,nombre:"",version:0};
            this.datosForma.patchValue(obj);
            return;
        }
    }
    
    formToInstance() {
        return true;
    }

}

