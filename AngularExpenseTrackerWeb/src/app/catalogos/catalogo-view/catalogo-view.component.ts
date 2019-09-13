import { Component, Input, OnInit, ViewChild, ComponentFactoryResolver, OnDestroy } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { CuentaListComponent } from "../cuenta-list/cuenta-list.component";
import { CuentaEditComponent } from "../cuenta-edit/cuenta-edit.component";

import { MonedaListComponent } from "../moneda-list/moneda-list.component";
import { MonedaEditComponent } from "../moneda-edit/moneda-edit.component";

import { TipocambioListComponent } from "../tipocambio-list/tipocambio-list.component";
import { TipocambioEditComponent } from "../tipocambio-edit/tipocambio-edit.component";

import { ProveedorListComponent } from "../proveedor-list/proveedor-list.component";
import { ProveedorEditComponent } from "../proveedor-edit/proveedor-edit.component";

import { CategoriaListComponent } from "../categoria-list/categoria-list.component";
import { CategoriaEditComponent } from "../categoria-edit/categoria-edit.component";

import { ArticuloListComponent } from "../articulo-list/articulo-list.component";
import { ArticuloEditComponent } from "../articulo-edit/articulo-edit.component";

import { GrupoListComponent } from "../grupo-list/grupo-list.component";
import { GrupoEditComponent } from "../grupo-edit/grupo-edit.component";


import { GrupoarticuloListComponent } from "../grupoarticulo-list/grupoarticulo-list.component";
import { GrupoarticuloEditComponent } from "../grupoarticulo-edit/grupoarticulo-edit.component";

import { PaginacionObject } from "../../model/paginacion";

import { ComponentListInterface } from "../../model/componentlist.interface";

import { ViewDirective } from "../view.directive";
import { ViewEditDirective } from "../viewedit.directive";

import * as $ from 'jquery';

@Component({
  selector: 'app-catalogo-view',
  templateUrl: './catalogo-view.component.html',
  styleUrls: ['./catalogo-view.component.css']
})
export class CatalogoViewComponent implements OnInit {

    @ViewChild(ViewDirective, {static: true}) vistalista: ViewDirective;
    @ViewChild(ViewEditDirective, {static: true}) vistaedicion: ViewEditDirective;
    
    paginacionObject:PaginacionObject = new PaginacionObject();
    
    components = {
                    proveedor   : { list : ProveedorListComponent, edit: ProveedorEditComponent },
                    cuenta      : { list : CuentaListComponent, edit: CuentaEditComponent },
                    moneda      : { list : MonedaListComponent, edit: MonedaEditComponent },
                    tipocambio  : { list : TipocambioListComponent, edit: TipocambioEditComponent },
                    categoria   : { list : CategoriaListComponent, edit: CategoriaEditComponent },
                    articulo    : { list : ArticuloListComponent, edit: ArticuloEditComponent },
                    grupo       : { list : GrupoListComponent, edit: GrupoEditComponent },
                    grupoarticulo: {list : GrupoarticuloListComponent,  edit: GrupoarticuloEditComponent }
                };
    
    constructor(private componentFactoryResolver: ComponentFactoryResolver,private route: ActivatedRoute) {
        
    }

    ngOnInit() {
        
        //cada ves que cambie la ruta, cargar el componente y subcomponente
        this.route.url.subscribe(url =>{
            this.loadComponent();
            this.loadSubComponent();
       });
    }
    
    loadComponent() {
        
        if(!this.components[this.route.snapshot.paramMap.get("catalogo")] || this.components[this.route.snapshot.paramMap.get("catalogo")].list == null ) {
            alert("error catalogo no encontrado "+this.route.snapshot.paramMap.get("catalogo"));
            return;
        }
        
        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.components[this.route.snapshot.paramMap.get("catalogo")].list);
        const viewContainerRef = this.vistalista.viewContainerRef;
        viewContainerRef.clear();
        const componentRef = viewContainerRef.createComponent(componentFactory);
        (<ComponentListInterface>componentRef.instance).paginacion = this.paginacionObject;
    }
    
    loadSubComponent() {
        
        if(!this.components[this.route.snapshot.paramMap.get("catalogo")] || this.components[this.route.snapshot.paramMap.get("catalogo")].edit == null ) {
            alert("error catalogo no encontrado "+this.route.snapshot.paramMap.get("catalogo"));
            return;
        }
        
        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.components[this.route.snapshot.paramMap.get("catalogo")].edit);
        const viewContainerRef = this.vistaedicion.viewContainerRef;
        viewContainerRef.clear();
        const componentRef = viewContainerRef.createComponent(componentFactory);
    }
   
}
