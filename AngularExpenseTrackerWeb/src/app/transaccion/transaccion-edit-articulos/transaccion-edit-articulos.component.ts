
import { Component, OnInit,Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { DatepickerOptions } from 'ng2-datepicker';

import * as esLocale from 'date-fns/locale/es';
import * as $ from 'jquery';

import { ParentEditComponent } from "../../catalogos/parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { PaginacionService } from '../../services/paginacion.service';
import { EditItemService } from '../../services/edit.item.service';
import { ListaService } from '../../services/lista.service';
import { UserService } from "../../services/user.service";

import { SearchObject } from '../../model/searchobject';

@Component({
  selector: 'app-transaccion-edit-articulos',
  templateUrl: './transaccion-edit-articulos.component.html',
  styleUrls: ['./transaccion-edit-articulos.component.css']
})
export class TransaccionEditArticulosComponent implements OnInit {
    
    idtransaccion:number = 0;
    sumaArticulos:number = 0;

    dropdownSettingsArticulos = {
            singleSelection: true,
            idField: 'id',
            textField: 'name',
            allowSearchFilter: true,
            closeDropDownOnSelection: true,
        };
    
    listaArticulos = [
                      {id: 1, name:"adasdasd"},
                      {id: 2, name:"vsadsasd"},
                      ];
    
    articuloSeleccionado = null;
    
    datosDetalleForma = this.fb.group({
        idarticulo: [""],
        cantidad: [""],
        subtotal : [""],
        iva : [1],
        total: [0],
    });
    
    tablaArticulos = [];
    
    constructor(private persistenceService:PersistenceService,
            private fb: FormBuilder,
            private edititemService:EditItemService,
            private paginacionService:PaginacionService,
            private listaService:ListaService,
           ) {
        
    }

    ngOnInit() {
                
        //obtener el catalogo de articulos
        this.listaService.getArticulosNombre().subscribe( res => {this.listaArticulos = res.data });
        
        //cada ves que se presione el boton de editar, obtener lista de articulos correspondientes a la transaccion seleccionada
        this.edititemService.editItemEvent().subscribe( message => {this.editTransaccionClick(message);});
    }
    
    editTransaccionClick(item:any) {
        
        this.listar(item);
        
        if( item != null )
            this.idtransaccion =  item.idtransaccion;
        else 
            this.idtransaccion = 0;
    }
    
    agregar() {
        
        if( this.articuloSeleccionado == null || this.articuloSeleccionado.length <= 0 ) {
            alert("seleccione un articulo");
            return;
        }
        
        if( this.datosDetalleForma.value.subtotal.length <= 0 ) {
            alert("Debe especificar un total para poder agregar articulo");
            return;
        }
        
        if( this.datosDetalleForma.value.cantidad.length <= 0 ) {
            alert("Debe especificar una cantidad para poder agregar articulo");
            return;
        }
        
        this.datosDetalleForma.value.idarticulo = this.articuloSeleccionado[0].id;
        this.datosDetalleForma.value.idtransaccion = this.idtransaccion;
        this.datosDetalleForma.value.cantidad = parseFloat(this.datosDetalleForma.value.cantidad);
        this.datosDetalleForma.value.subtotal = parseFloat(this.datosDetalleForma.value.subtotal.split(',').join('')) * 100;
        this.datosDetalleForma.value.subtotal = parseInt(this.datosDetalleForma.value.subtotal);
        this.datosDetalleForma.value.iva = parseFloat(this.datosDetalleForma.value.iva);
        this.datosDetalleForma.value.total = parseInt(this.datosDetalleForma.value.subtotal) * parseFloat(this.datosDetalleForma.value.iva);
        this.datosDetalleForma.value.total = parseInt(this.datosDetalleForma.value.total);
        
        /*if( $('#sumarTransaccionCheck').parent().hasClass("off") )
            this.datosDetalleForma.value.sumaratransaccion = false;
        else
            this.datosDetalleForma.value.sumaratransaccion = true;*/
        
        this.persistenceService.setObject("transaccionarticulo", this.datosDetalleForma.value).subscribe(message => {this.agregarResponse(message);});

    }
    
    agregarResponse(res) {
        
        //verificar que el resultado no venga vacio
        if( res == null || res == undefined ) { alert("error al intentar agregar articulo"); return;}
        
        //si la operacion no fue exitosa indicarlo y salir
        if( !res.success) { alert("error al intentar agregar articulo, "+res.message); return; }
        
        //inicializar todos los campos de captura de articulo
        this.datosDetalleForma.controls.cantidad.setValue("");
        this.datosDetalleForma.controls.subtotal.setValue("");
        this.articuloSeleccionado = null;
        
        //volver a listar la lista de articulos
        this.listar({idtransaccion:this.idtransaccion});
    }
    
    listar(obj:any) {
        
        //si es un nuevo registro entonces asignar un arreglo vacio
        if( obj == null ) {
            this.tablaArticulos = [];
            return;
        }
        this.sumaArticulos = 0;
        var search:SearchObject = new SearchObject();
        search.catalogo = "transaccionarticulo";
        search.addSearchField("idtransaccion", obj.idtransaccion+"");
        search.limite = search.pagina = 0;
        this.persistenceService.getList(search).subscribe( res => {this.listarResponse(res); } );
    }
    
    listarResponse(res:any) {
        
        if( res == null || res == undefined ) {
            alert("error no hubo respuesta del servidor");
            return;
        }
        
        if( res.success == false ) {
            alert(res.message);
            return;
        }
       
        if( res.data )
            this.tablaArticulos = res.data; 
        else 
            this.tablaArticulos = [];
        
        for( var i = 0; i < this.tablaArticulos.length; i++ ) {
            this.sumaArticulos+=this.tablaArticulos[i].total;
        }
      
    }
    
    remove(obj:any) {
        
        //confirmar el borrado de registro
        if( !confirm("Seguro que desea eliminar articulo \""+obj.articuloNombre+"\"") )
            return;
        
        //armar la busqueda con idtransaccion, idarticulo que son las llaves del catalogo
        var search:SearchObject = new SearchObject();
        search.catalogo = "transaccionarticulo";
        search.addSearchField("idtransaccion", obj.idtransaccion+"");
        search.addSearchField("idarticulo", obj.idarticulo+"");
        
        //intentar eliminar el registro y llamar a removeResponse cuando responda el servicio de eliminar
        this.persistenceService.removeObject(search).subscribe(message=>{this.removeResponse(message);});
    }
    
    removeResponse(res) {
        
        if( res == null || res == undefined ) {
            alert("error desconocido al intentar eliminar registro");
            return;
        }
        
        if( res.success == false ) {
            alert(res.message);
            return;
        }
        
        //volver a listar la lista de articulos
        this.listar({idtransaccion:this.idtransaccion});
    }

}
