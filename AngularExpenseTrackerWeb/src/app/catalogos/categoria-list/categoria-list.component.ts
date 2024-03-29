import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

import { PaginacionObject, PaginacionItem } from '../../model/paginacion';
import { SearchObject, SearchField } from '../../model/searchobject';

import { ParentListComponent } from "../parent-list.component";
import * as $ from 'jquery';

@Component({
  selector: 'app-categoria-list',
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.css']
})
export class CategoriaListComponent extends ParentListComponent implements OnInit {

    @Input() paginacion:PaginacionObject;
    search : SearchObject = new SearchObject();

    constructor(private edititemService: EditItemService,private persistenceService:PersistenceService,private paginacionService:PaginacionService,private route: ActivatedRoute) {
        super(edititemService,paginacionService,persistenceService,route);
    }

    ngOnInit() {
        
        this.search.catalogo = "categoria";
        this.search.searchFields = [];
        this.search.orderFields = [ "idcategoria"];
        
        //obtener lista
        this.getResultsList(this.search, this.paginacion);
        
        //se ejecuta cada ves que se cambia el numero de pagina
        this.paginacionService.cambioPaginaEvent().subscribe(message=> { this.getResultsList(this.search, this.paginacion); });
    }
    
    ordenarporid() {
        this.search.orderFields = [ "idcategoria"];
        $("table i").removeClass("text-info");
        $("#col_id").addClass("text-info");
        this.getResultsList(this.search, this.paginacion);
    }
    
    ordenarpornombre() {
        this.search.orderFields = [ "nombre"];
        $("table i").removeClass("text-info");
        $("#col_nombre").addClass("text-info");
        this.getResultsList(this.search, this.paginacion);
    }
}
