import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PersistenceService } from '../services/persistence.service';
import { SearchObject, SearchField } from '../model/searchobject';

import * as $ from "jquery";

@Component({
  selector: 'app-cuentas-resumen',
  templateUrl: './cuentas-resumen.component.html',
  styleUrls: ['./cuentas-resumen.component.css']
})
export class CuentasResumenComponent implements OnInit {

    search: SearchObject = new SearchObject();
    nombreMonedaBase = null;

    constructor(
                private persistenceService:PersistenceService,
                private router: Router 
                ) { }

    
    /*cuentas = [

               {   nombre:"Cuentas de efectivo", saldo:1234567.78,
                   cuentas: [
                            { nombre:"cuenta1", saldo:100, moneda:"MXN",  },
                            { nombre:"cuenta2", saldo:200, moneda:"USD", saldoMonedaBase:"4000" },
                            { nombre:"cuenta2", saldo:200, moneda:"MXN",  },
                             ]
                   },
               { nombre:"Cuentas de credito", saldo:123.12,
                       cuentas: [
                           { nombre:"cuenta1", saldo:100, moneda:"MXN",  },
                       ]
               },
               ];*/
    
    cuentas = [];
    
    granTotal = 0;
    
    ngOnInit() {
        this.getCuentas();
    }
    
    getCuentas():void {
       
      //actualizar los parametros con los que se va a hacer la busqueda
        this.search.limite = 0;
        this.search.pagina = 0;
        this.search.catalogo = "cuenta";
        
        this.persistenceService.getList(this.search).subscribe( res => { this.transformData(res);} );
       
    }
    
    transformData(restemp):void {
        
        var res = restemp.data;
        var tipoCuenta = "";
        var data = [];
        
        for(var i = 0; i < res.length; i++ ) {
            
            if( this.nombreMonedaBase == null && res[i].monedaBase == true )
                this.nombreMonedaBase = res[i].moneda;
            
            if( res[i].tipo != tipoCuenta ) {
                
                //guardar el tipo de cuenta
                tipoCuenta = res[i].tipo;
                
                //agregar grupo de cuentas de tipo res[i].tipo
                data.push({ nombre: res[i].tipo, saldo: 0, cuentas : [] });

            } 
            
            //agregar la cuenta a este grupo de cuentas
            data[data.length-1].cuentas.push(res[i]);
            
            //acumular total
            data[data.length-1].saldo+= res[i].saldoMonedaBase;
            
            //acumular gran total
            this.granTotal+=res[i].saldoMonedaBase;

        }
        
        //actualizar el resultado en la pagina
        this.cuentas = data;
    }
    
    detalleClick(detail) {
        this.router.navigateByUrl("/transacciones/cuenta/"+detail.idcuenta);
    }
    
    detalleCuentas(itemx) {
        var temp = "";
        for( var i = 0; i < itemx.cuentas.length; i++ )
            temp+="c"+itemx.cuentas[i].idcuenta;
        temp = "/transacciones/cuenta/"+temp;
        this.router.navigateByUrl(temp);
    }
    
    cardclick(event,item) {
        
        if( $(event.target).is("i") ) {
            
            if( $(event.target).hasClass("fa-minus-circle") )
                $(event.target).removeClass("fa-minus-circle").addClass("fa-plus-circle");
            else
                $(event.target).removeClass("fa-plus-circle").addClass("fa-minus-circle");
            
            return;
        }
            
        if( $(event.target).find("i").hasClass("fa-minus-circle") )
            $(event.target).find("i").removeClass("fa-minus-circle").addClass("fa-plus-circle");
        else
            $(event.target).find("i").removeClass("fa-plus-circle").addClass("fa-minus-circle");

    }

}
