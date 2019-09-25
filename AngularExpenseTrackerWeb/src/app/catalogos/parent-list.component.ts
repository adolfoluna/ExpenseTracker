import { ActivatedRoute, Router } from '@angular/router';

import { PaginacionService } from '../services/paginacion.service';
import { PersistenceService } from '../services/persistence.service';
import {SearchObject, SearchField} from '../model/searchobject';
import { PaginacionObject} from '../model/paginacion';

import { EditItemService } from '../services/edit.item.service';

import * as $ from 'jquery';

export class ParentListComponent  {
    
    public data = [];
    public loading:boolean = false;
    
    constructor(private editItemService:EditItemService,private pgService:PaginacionService,private persistService:PersistenceService,private ruta: ActivatedRoute,) {
        
    }
    
    editRowClick(item:any,catalogo:string) {
        this.editItemService.triggerEditItem(item,catalogo);
    }
    
    newRowClick(catalogo:string) {
        this.editItemService.triggerEditItem(null,null);
    }
    
    getResultsList(search:SearchObject,paginacion:PaginacionObject) {
        
        if( this.ruta.snapshot.paramMap.get("catalogo") != null && this.ruta.snapshot.paramMap.get("catalogo") != search.catalogo  )
            return;
        
        //activar el tab de lista en casp de que no este activado
        $("#tablist").addClass("active");
        $("#tabedit").removeClass("active");
        
        //indicar que se esta cargando la info
        this.loading = true;
        
        //mostrar que se estan cargando los resultados
        $("#tableLoading").show();
        
        search.limite = paginacion.limite;
        search.pagina = paginacion.pagina;
        
        this.persistService.getList(search).subscribe( res => this.resultListResponse(res, paginacion) );
    }
    
    resultListResponse(res:any,paginacion:PaginacionObject) {
        
        //indicar que ya no se esta cargando la info
        this.loading = false;
        
        $("#tableLoading").hide();
       
        if( res == null || res == undefined ) {
            alert("error no hubo respuesta del servidor");
            return;
        }
        
        if( res.success == false ) {
            alert(res.message);
            return;
        }
        
        paginacion.numResultados = res.results;
        
        if( res.data )
            this.data = res.data; 
        else 
            this.data = [];
        
        this.pgService.agregarPaginas(paginacion);
    }
    
}