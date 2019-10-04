import { Component, OnInit, Input } from '@angular/core';
import { format } from 'date-fns'
import { ActivatedRoute, Router } from '@angular/router';

import { enviroment } from '../../model/enviroment';

import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { ViewItemService } from '../../services/view.item.service';
import { BuscarTransaccionesService } from "../../services/buscar.transacciones.service";
import { UserService } from "../../services/user.service";

import { AdvancedSearchGroup, AdvancedSearchField } from "../../model/advancedsearchobject";
import { PaginacionObject, PaginacionItem } from '../../model/paginacion';
import { SearchObject, SearchField } from '../../model/searchobject';
import { ParametrosBusquedaTransaccion } from "../../model/parametros_transaccion";

import { ParentListComponent } from "../../catalogos/parent-list.component";

import * as moment from 'moment';
import * as $ from 'jquery';

@Component({
  selector: 'app-transaccion-list',
  templateUrl: './transaccion-list.component.html',
  styleUrls: ['./transaccion-list.component.css']
})
export class TransaccionListComponent extends ParentListComponent implements OnInit {

    paginacionObject:PaginacionObject = new PaginacionObject();
    search : SearchObject = new SearchObject();

    subtitulo = "";
    
    trolnum = 4;

    constructor(private edititemService: EditItemService,
                private viewitemService : ViewItemService,
                private persistenceService:PersistenceService,
                private paginacionService:PaginacionService,
                private buscarTransaccionesService: BuscarTransaccionesService,
                private route: ActivatedRoute,
                private userService:UserService) {
        super(edititemService,paginacionService,persistenceService,route);
    }
    
    formaParametros:ParametrosBusquedaTransaccion = new ParametrosBusquedaTransaccion();

    ngOnInit() {
        
        //consultar el rol que tiene el usuario
        this.trolnum = this.userService.getRoleNumber();
        
        //poner por default 50 resultados
        this.paginacionObject.limite = 50;

        //se ejecuta cada ves que se cambia el numero de pagina
        this.paginacionService.cambioPaginaEvent().subscribe(message=> { this.getList(); });
        
        //se ejecuta cada ves que se presiona el boton de buscar
        this.buscarTransaccionesService.buscarTransaccionesEvent().subscribe(message=>{ this.buscarTransacciones(message);});
    }
    
    getList() {
        this.data = [];
        this.search.catalogo = "transaccion";
        this.search.orderFields = [];
        this.getResultsList(this.search, this.paginacionObject);
    }
    
    clickDocument(type:string,item:any) {
        
        if( !item.hasOwnProperty(type) || item[type] == null ) {
            alert("transaccion sin "+type);
            return;
        }
        
        //mostrar documento en una nueva ventana
        window.open(enviroment.docsURL+item[type]);
        
    }
    
    generarSubtitulo(parametros:ParametrosBusquedaTransaccion) {
        
        if( parametros.idtransaccion != null && parametros.idtransaccion.length > 0 ) {
            this.subtitulo = "<b>Transaccion con Id</b> "+parametros.idtransaccion;
            return;
        }
        
        this.subtitulo ="";
        for(var h = 0; h < parametros.cuentas.length; h++ ) {
            if(h > 0 ) this.subtitulo = this.subtitulo + ",";
            else this.subtitulo = this.subtitulo + "<b>Cuenta(s):</b>";
            this.subtitulo = this.subtitulo + parametros.cuentas[h].name;
        }
        
        for(var h = 0; h < parametros.proveedores.length; h++ ) {
            if(h > 0 ) this.subtitulo = this.subtitulo + ",";
            else this.subtitulo = this.subtitulo + " <b>Provedor(es):</b>";
            this.subtitulo = this.subtitulo + parametros.proveedores[h].name;
        }
        
        for(var h = 0; h < parametros.articulos.length; h++ ) {
            if(h > 0 ) this.subtitulo = this.subtitulo + ",";
            else this.subtitulo = this.subtitulo + " <b>Articulos(es):</b>";
            this.subtitulo = this.subtitulo + parametros.articulos[h].name;
        }
        
        for(var h = 0; h < parametros.comprobantes.length; h++ ) {
            if(h > 0 ) this.subtitulo = this.subtitulo + ",";
            else this.subtitulo = this.subtitulo + " <b>Comprobante(s):</b>";
            this.subtitulo = this.subtitulo + parametros.comprobantes[h].name;
        }
        
        if( parametros.minfecha != null ) 
            this.subtitulo = this.subtitulo + " <b>Desde:</b>" + format(parametros.minfecha, "YYYY-MM-DD");
        
        if( parametros.maxfecha != null ) 
            this.subtitulo = this.subtitulo + " <b>Hasta:</b>" + format(parametros.maxfecha, "YYYY-MM-DD");
        
        if( this.subtitulo.length <= 0 )
            this.subtitulo = "Mostrando todas las transacciones";
        
    }
    
    buscarTransacciones(parametros:ParametrosBusquedaTransaccion) {
        
        this.search.advancedSearchGroups = [];
        
        this.generarSubtitulo(parametros);
        
        //this.addField("1","=","1",null);
        
        if( parametros.idtransaccion != null && parametros.idtransaccion.length > 0) {
            var aux:AdvancedSearchGroup = new AdvancedSearchGroup();
            aux.addField(null,"idtransaccion",parametros.idtransaccion,"=");
            this.search.addAdvancedSearchGroup(aux);
            this.getList();
            return;
        }
        
        if( parametros.cuentas.length > 0 ) {
            var aux:AdvancedSearchGroup = new AdvancedSearchGroup();
            for(var h = 0; h < parametros.cuentas.length; h++ ) 
               aux.addField("or", "idcuenta", parametros.cuentas[h].id, "=");
            this.search.addAdvancedSearchGroup(aux);
        }
        
        if( parametros.proveedores.length > 0 ) {
            var aux:AdvancedSearchGroup = new AdvancedSearchGroup();
            for( var h = 0; h < parametros.proveedores.length; h++ )
                aux.addField("or","proveedor",parametros.proveedores[h].id,"=");
            this.search.addAdvancedSearchGroup(aux);
        }
        
        if( parametros.articulos.length > 0 ) {
            console.log("agregando");
            var aux:AdvancedSearchGroup = new AdvancedSearchGroup();
            for( var h = 0; h < parametros.articulos.length; h++ ) 
                aux.addField("or","articulo",parametros.articulos[h].id,"=");
            this.search.addAdvancedSearchGroup(aux);
        }
        
        if( parametros.comprobantes.length > 0 ) {
            
            for( var h = 0; h < parametros.comprobantes.length; h++ ) {
                
                switch(parametros.comprobantes[h].id) {
                    case "ticket-true": this.search.addAndField("ticket", "null", "is not"); break; 
                    case "ticket-false": this.search.addAndField("ticket", "null", "is"); break;
                    case "factura-true": this.search.addAndField("factura", "null", "is not"); break;
                    case "factura-false": this.search.addAndField("factura", "null", "is"); break;
                    case "complemento-true": this.search.addAndField("complemento", "null", "is not"); break;
                    case "complemento-false": this.search.addAndField("complemento", "null", "is"); break;
                    case "voucher-true": this.search.addAndField("voucher", "null", "is not"); break;
                    case "voucher-false": this.search.addAndField("voucher", "null", "is"); break;
                    case "requierecomplemento":this.search.addAndField("requierecomplemento", "0", ">"); break;
                }

            }
            
        }
        
        if( parametros.minfecha != null ) 
            this.search.addAndField("fecha", format(parametros.minfecha, "YYYY-MM-DD"), ">=");
        
        if( parametros.maxfecha != null )
            this.search.addAndField("fecha", format(parametros.maxfecha, "YYYY-MM-DD"), "<=");
        
        //obtener resultados de busqueda
        this.getList();
        
    }
    
    buscarMesActual() {
        this.formaParametros.minfecha = moment().startOf('month').format('YYYY-MM-DD');
        this.formaParametros.maxfecha = moment().endOf('month').format('YYYY-MM-DD');
        this.buscarTransacciones(this.formaParametros);
    }
    
    buscar2MesAnterior() {
        var m = moment().subtract(2,'months');
        this.formaParametros.minfecha = m.startOf('month').format('YYYY-MM-DD');
        this.formaParametros.maxfecha = m.endOf('month').format('YYYY-MM-DD');
        this.buscarTransacciones(this.formaParametros);
    }
    
    buscarMesAnterior() {
        var m = moment().subtract(1,'months');
        this.formaParametros.minfecha = m.startOf('month').format('YYYY-MM-DD');
        this.formaParametros.maxfecha = m.endOf('month').format('YYYY-MM-DD');
        this.buscarTransacciones(this.formaParametros);
    }
    
    buscarSinComplemento() {
        this.formaParametros.comprobantes = [
                         {id:"requierecomplemento",name:"Requiere complemento"},
                         {id:"complemento-false",name:"Sin complemento"}
                         ];
        this.buscarTransacciones(this.formaParametros);
    }
    
    borrarParametrosBusqueda() {
        this.formaParametros = new ParametrosBusquedaTransaccion();
        this.buscarTransacciones(this.formaParametros);
    }
    
    addField(fieldName,operator,value,union) {
        var aux:AdvancedSearchGroup = new AdvancedSearchGroup();
        aux.union = union;
        aux.addField(null,fieldName,value,operator);
        this.search.addAdvancedSearchGroup(aux);
    }
    
    viewRowClick(temp) {
        //disparar el evento de ver una transaccion, para que lo recoja el componente transaccion-view-detail
        this.viewitemService.triggerViewItem(temp);
    }

}