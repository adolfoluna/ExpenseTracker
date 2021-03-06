import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';

import { PaginacionObject, PaginacionItem } from '../../model/paginacion';
import { SearchObject, SearchField } from '../../model/searchobject';

import { ParentListComponent } from "../parent-list.component";

@Component({
  selector: 'app-cuenta-list',
  templateUrl: './cuenta-list.component.html',
  styleUrls: ['./cuenta-list.component.css']
})
export class CuentaListComponent extends ParentListComponent implements OnInit {

    @Input() paginacion:PaginacionObject;
    search : SearchObject = new SearchObject();

    constructor(private edititemService: EditItemService,private persistenceService:PersistenceService,private paginacionService:PaginacionService,private route: ActivatedRoute) {
        super(edititemService,paginacionService,persistenceService,route);
    }

    ngOnInit() {

        //obtener lista
        this.getList();
        
        //se ejecuta cada ves que se cambia el numero de pagina
        this.paginacionService.cambioPaginaEvent().subscribe(message=> { this.getList(); });
    }
    
    getList() {

        this.search.catalogo = "cuenta";
        this.search.searchFields = [];
        this.search.orderFields = [ "idcuenta"];
        
        this.getResultsList(this.search, this.paginacion);

    }

}
