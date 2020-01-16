import { ActivatedRoute, Router } from '@angular/router';

import * as $ from 'jquery';

import { PaginacionService } from '../services/paginacion.service';
import { PersistenceService } from '../services/persistence.service';
import {SearchObject, SearchField} from '../model/searchobject';

export class ParentEditComponent  {
    
    private catalogo:string = null;
    private forma:any = null;
    private formToInstancef:any = null;
    private instanceToFormf:any = null; 
    private keys:string[] = [];

    public mostrarBotonEliminar:boolean = false;
    
    public botonesDeshabilitados:boolean = false;
    
    constructor(private pgService:PaginacionService,private persistService:PersistenceService,private ruta: ActivatedRoute) {
      
    }
    
    updateValues(catalogo:string,keys:string[],forma:any,formToInstancef:any,instanceToFormf:any) {
        this.catalogo = catalogo;
        this.forma = forma;
        this.formToInstancef = formToInstancef;
        this.instanceToFormf = instanceToFormf;
        this.keys = keys;
    }
    
    activarListTab() {
        $("#tablist").addClass("active");
        $("#tabedit").removeClass("active");
        this.pgService.triggerCambioPagina();
    }
    
    activarEditTab() {
        $("#tablist").removeClass("active");
        $("#tabedit").addClass("active");
    }
    
    getInstanceFromServer(catalogo:string,item:any) {
                    
        //si el registro es nuevo entonces solo inicializar valores
        //y mostrar el div de editar
        if( item == null ) {
            
            if( this.instanceToFormf != null )
                this.instanceToFormf(null);
            
            this.mostrarBotonEliminar = false;
            
            this.activarEditTab();
            
            return;
        }
        
      //descartar todos los suscriptores al evento de edicion, excepto el catalogo que esta activo
        if( catalogo != item._catalogo ) 
            return;
       
        this.mostrarBotonEliminar = true;
        
        var search : SearchObject = new SearchObject();
        search.catalogo = this.catalogo;
        search.searchFields = [ ];
    
        for( var i = 0; i < this.keys.length; i++ ) {
            var searchItem = new SearchField(this.keys[i], item[this.keys[i]])
            search.searchFields.push(searchItem);   
        }
        
        this.persistService.getObject(search).subscribe( res => {this.getInstanceFromServerResponse(res)});
    }
    
    getInstanceFromServerResponse(res) {
        
        if( res == null || res == undefined ) {
            alert("error, no hubo respuesta del servidor");
            return;
        }
        
        if( res.success == false ) {
            alert(res.message);
            return;
        }
        
        if( this.instanceToFormf != null )
            this.instanceToFormf(res.data);
        
        this.forma.patchValue(res.data);
        
        this.activarEditTab();
    }
        
    save() {

        if( this.formToInstancef == null || this.formToInstancef == undefined ) {
            alert("error en componente no existe la funcion formToInstance")
            return;
        }
        
        //si la funcion regresa false, entonces salir y no proceder
        if( this.formToInstancef == null || this.formToInstancef == undefined ) {
            alert("funcion formToInstancef no encontrada");
            return;
        }
        
        if( !this.formToInstancef() )
            return;
        
        this.botonesDeshabilitados = true;
        
        this.persistService.setObject(this.catalogo,this.forma.value).subscribe( res => {
            
            this.botonesDeshabilitados = false;
            
            if( res.success == false ) {
                alert(res.message);
                return;
            }
            
            this.activarListTab();
            
        });
        
    }
    
    remove() {
        
        if( this.formToInstancef != null)
            this.formToInstancef();
        
        var temp:string = "";
    
        for(var i = 0; i < this.keys.length ;i++) {
            if( i <= 0 )
                temp+=this.forma.value[this.keys[i]];
            else
                temp+="-"+this.forma.value[this.keys[i]];
        }
        
        if(!confirm("Seguro que desea eliminar registro con id "+temp))
            return;
        
        var search : SearchObject = new SearchObject();
        search.catalogo = this.catalogo;
        search.searchFields = [ ];
        
        for(var i = 0; i < this.keys.length ;i++) {
            var aux = new SearchField(this.keys[i],this.forma.value[this.keys[i]]);
            search.searchFields.push(aux);
        }
        this.botonesDeshabilitados = true;
        this.persistService.removeObject(search).subscribe( res => {
                this.botonesDeshabilitados = false;
                if( res == null || res == undefined ) {
                    alert("error, no se recibio respuesta del servidor");
                    return;
                }
                
                if( res.success == false ) {
                    alert(res.message);
                    return;
                }
                
                this.activarListTab();
                    
            });
    }
    
    backClick() {
        this.activarListTab();   
    }
 
}