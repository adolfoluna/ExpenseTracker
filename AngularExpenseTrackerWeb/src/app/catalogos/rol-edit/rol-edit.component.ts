import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ParentEditComponent } from "../parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

@Component({
  selector: 'app-rol-edit',
  templateUrl: './rol-edit.component.html',
  styleUrls: ['./rol-edit.component.css']
})
export class RolEditComponent extends ParentEditComponent implements OnInit {
    
    listaRoles = [ "Seleccionar","Admin", "Operador","Solo lectura","Contador"];
    
    datosForma = this.fb.group({
        idrol : [0],
        rolnum : [0],
        ruta : [""],
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
        
        this.updateValues("rol",["idrol"], this.datosForma, this.formToInstance,this.instanceToForm);

        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => {this.getInstanceFromServer("rol",message); });
    }
    
    instanceToForm(obj:any) {
        if( obj == null ) {
            obj = { idrol:0,rolnum:2,ruta:"",version:0};
            this.datosForma.patchValue(obj);
        }
    }
    
    formToInstance() {
        
        //validar que se eliga un perfil
        if( this.datosForma.controls["rolnum"].value <= 0 ) {
            alert("seleccionar perfil");
            return false;
        }
        
        //validar que el nombre de usuario no este en blanco
        if( this.datosForma.controls["ruta"].value.toString().trim().length <= 0 ) {
            alert("Ruta en blanco");
            return false;
        }
        
        return true;
    }
}