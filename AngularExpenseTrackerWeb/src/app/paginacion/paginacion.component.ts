import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PaginacionObject } from '../model/paginacion';
import { PaginacionService } from '../services/paginacion.service';

@Component({
  selector: 'app-paginacion',
  templateUrl: './paginacion.component.html',
  styleUrls: ['./paginacion.component.css']
})
export class PaginacionComponent implements OnInit {

    @Input() paginacion:PaginacionObject;
    
    constructor(private route: ActivatedRoute,private paginacionService:PaginacionService ) { }

    ngOnInit() {
        
    }
    
    firstPage() { 
        this.paginacion.pagina = 0;
        this.paginacionService.triggerCambioPagina();
    }
    
    nextPage() {
       this.paginacion.pagina++;
       this.paginacionService.triggerCambioPagina();
    }
    
    previousPage() {
        this.paginacion.pagina--;
        this.paginacionService.triggerCambioPagina();
    }
    
    lastPage() {
        this.paginacion.pagina = this.paginacion.numeroPaginas - 1;
        this.paginacionService.triggerCambioPagina();
    }
    
    pageClick(pageNumber) {
        this.paginacion.pagina = pageNumber - 1;
        this.paginacionService.triggerCambioPagina();
    }
    
    limitClick(limite) {
        this.paginacion.limite = limite;
        this.paginacionService.triggerCambioPagina();
    }
    
    refresh() {
        this.paginacionService.triggerCambioPagina();
    }

}
