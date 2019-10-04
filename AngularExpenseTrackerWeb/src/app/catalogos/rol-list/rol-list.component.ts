import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { PaginacionObject, PaginacionItem} from '../../model/paginacion';
import { PersistenceService } from '../../services/persistence.service';
import { SearchObject, SearchField} from '../../model/searchobject';

import { ParentListComponent } from "../parent-list.component";

import * as $ from 'jquery';

@Component({
  selector: 'app-rol-list',
  templateUrl: './rol-list.component.html',
  styleUrls: ['./rol-list.component.css']
})
export class RolListComponent extends ParentListComponent implements OnInit {
    
    search : SearchObject = new SearchObject();
    
    @Input() paginacion:PaginacionObject;
        
    constructor(    private persistenceService:PersistenceService,
                    private paginacionService:PaginacionService,
                    private edititemService: EditItemService,
                    private route: ActivatedRoute
                    ) {
        super(edititemService,paginacionService,persistenceService,route);
    }

    ngOnInit() {
        
        this.getList();
        
        //se ejecuta cada ves que se cambia el numero de pagina
        this.paginacionService.cambioPaginaEvent().subscribe(message=> { this.getList(); });
        
    }
    
    getList() {
        
        this.search.catalogo = "rol";
        this.search.searchFields = [];
        this.getResultsList(this.search, this.paginacion);
        
    }
    
    ordenarporid() {
        this.search.orderFields = [ "idrol"];
        $("table i").removeClass("text-info");
        $("#col_id").addClass("text-info");
        this.getResultsList(this.search, this.paginacion);
    }
    
    ordenarporrolnum() {
        this.search.orderFields = [ "rolnum"];
        $("table i").removeClass("text-info");
        $("#col_rolnum").addClass("text-info");
        this.getResultsList(this.search, this.paginacion);
    }
}