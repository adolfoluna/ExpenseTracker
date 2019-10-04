import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ParentEditComponent } from "../parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { ListaService } from '../../services/lista.service';
import {Md5} from 'ts-md5/dist/md5';

import { SearchObject } from '../../model/searchobject';

@Component({
  selector: 'app-usuario-edit',
  templateUrl: './usuario-edit.component.html',
  styleUrls: ['./usuario-edit.component.css']
})
export class UsuarioEditComponent extends ParentEditComponent implements OnInit {
    
    listaRoles = [ "Seleccionar","Admin", "Operador","Solo lectura","Contador"];
 
    datosForma = this.fb.group({
        idusuario : [0],
        usuario : [""],
        rolnum: [0],
        clave : [""],
        token: [""],
        activo : [ false ], 
        version: [0],
    });
    
    monedas = [];

    constructor(private persistenceService:PersistenceService,
                private fb: FormBuilder,
                private edititemService:EditItemService,
                private paginacionService:PaginacionService,
                private listaService : ListaService,
                private route: ActivatedRoute,) {
        
        super(paginacionService,persistenceService,route);
       
    }
    
    ngOnInit() {
        
        this.updateValues("usuario",["idusuario"], this.datosForma, this.formToInstance,this.instanceToForm);

        //se ejecuta cada ves que se presiona el boton de editar registro
        this.edititemService.editItemEvent().subscribe( message => { this.getInstanceFromServer("usuario",message); });
    }
    
    instanceToForm(obj:any) {
        
        if( obj == null ) {
            obj = { idusuario:0,usuario:"",clave:"",version:0,token:"",rolnum:1,activo:false};
            this.datosForma.patchValue(obj);
            return;
        }
        
        //poner la clave en blanco
        obj.clave = "";
        
    }
    
    formToInstance() {
        
        //validar que se eliga un perfil
        if( this.datosForma.controls["rolnum"].value <= 0 ) {
            alert("seleccionar perfil");
            return false;
        }
        
        //validar que el nombre de usuario no este en blanco
        if( this.datosForma.controls["usuario"].value.toString().trim().length <= 0 ) {
            alert("usuario en blanco");
            return false;
        }
        
        if( this.datosForma.controls["clave"].value.toString().trim().length > 0 ) {
            //cifrar la clave con MD5
            var md5 = new Md5();
            var pwd = md5.appendStr(this.datosForma.controls["clave"].value).end().toString();
            
            //actualizar la clave con cifrado md5
            this.datosForma.value.clave = pwd;
        }
                
        return true;
    }
    
}