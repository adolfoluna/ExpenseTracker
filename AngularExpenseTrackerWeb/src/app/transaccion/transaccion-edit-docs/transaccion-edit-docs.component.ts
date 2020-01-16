
import { Component, OnInit,Input } from '@angular/core';
import {  FormBuilder } from '@angular/forms';

import { enviroment } from '../../model/enviroment';

import { ParentEditComponent } from "../../catalogos/parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { EditItemService } from '../../services/edit.item.service';

import * as $ from 'jquery';

declare var show_modal: any;

@Component({
  selector: 'app-transaccion-edit-docs',
  templateUrl: './transaccion-edit-docs.component.html',
  styleUrls: ['./transaccion-edit-docs.component.css'],
})
export class TransaccionEditDocsComponent implements OnInit {

    @Input() idtransaccion:number;
    
    upload_file_progress = "0%";
    upload_file_message = "";
    upload_file_tittle = "";
    
    listaTiposArchivo = [
                         { val:"ticket", text:"Ticket", file: null },
                         { val:"factura", text:"Factura", file: null },
                         { val:"complemento", text:"Complemento de pago", file: null },
                         { val:"voucher", text:"Vale/Voucher", file: null },
                         { val:"transferencia", text:"Comprobante de transferencia", file: null },
                         { val:"cheque", text:"Cheque", file: null },
                         ];
    
    listaArchivos = [];
    
    botonAgregarDeshabilitado:boolean = true;
    
    datosForma = this.fb.group( {
        tipoarchivo: ["ticket"],
        file: [""],
    });
    
    transaccionRow = null;
    
    constructor(private persistenceService:PersistenceService,
                private fb: FormBuilder,
                private edititemService:EditItemService
                ) {
        
    }

    ngOnInit() {
      //cada ves que se presione el boton de editar, obtener lista de articulos correspondientes a la transaccion seleccionada
      this.edititemService.editItemEvent().subscribe( message => {this.editTransaccionClick(message);});
    }
    
    tipoArchivoChange() {
        
        //si no se ha seleccionado ningun archivo entonces deshabilitar boton y salir
        if( !(this.datosForma.get("file").value instanceof Object) ) {
            this.botonAgregarDeshabilitado = true;
            return;
        }
        
        //tomar el tipo de archivo que se selecciono
        var temp = this.datosForma.get("tipoarchivo").value;
        
        //recorrer todos los archivos que tiene la transaccion
        for( var i = 0; i < this.listaArchivos.length; i++ ) {
            
            //si el archivo ya esta agregado entonces deshabilitar boton
            if( temp == this.listaArchivos[i].val ) {
                this.botonAgregarDeshabilitado = true;
                return;
            }
            
        }
        
        //no se encontro agregado este documento por lo tanto habilitar boton de agregar
        this.botonAgregarDeshabilitado = false;
        
    }
    
    editTransaccionClick(obj) {
        
        //guarda el registro de la transaccion que se esta editando para tomar los valores
        this.transaccionRow = obj;
        
        //actualizar el arreglo listaArchivos con los archivos que tiene la transaccion
        this.updateListaArchivos();
    }
    
    uploadFile() {
        
        var tipo = this.datosForma.get("tipoarchivo").value;
        
        
        //si el registro que se esta editando es nuevo entonces no se pueden agregar documentos
        //a una transaccion que todavia no existe
        if( this.transaccionRow == null ) {
            alert("primero debe escoger la opcion de Guardar para poder guardar archivos");
            return;
        }
        
        if( !(this.datosForma.get("file").value instanceof Object) ) {
            alert("error archivo no encontrado o no seleccionado");
            return;
        }
        
        this.upload_file_tittle =tipo;
        this.upload_file_message = "";
        this.upload_file_progress = "0%";
        
        //deshabilitar boton de cerrar ventana
        $("#closeWindowButton").attr("disabled","disabled");
        
        this.transaccionRow.tipoComprobante = tipo;
        
        const formData = new FormData();
        formData.append('file', this.datosForma.get("file").value);
        this.persistenceService.uploadFile(this.idtransaccion,tipo,formData).subscribe(message=>{this.uploadFileResponse(message)});
        this.datosForma.controls["file"].setValue("");
        
    }
    
    uploadFileResponse(obj:any) {
       
        if( obj == null || obj == undefined ) {
            alert("error al intentar guardar documento");
            return;
        }
        
        //si el objeto tiene la propiedad status significa que fue un progreso o un evento
        if( obj.status && obj.status == "progress" ) {
            this.onFileUploadProgress(obj.message);
            return;
        }
        
        //si el objeto tiene la propiedad status entonces es un evento http
        if( obj.status ) return;
        
        //si la operacion no es exitosa indicarlo y salir
        if( obj.success == false ) {
            $("#closeWindowButton").removeAttr("disabled");
            this.upload_file_message = "error al intentar guardar documento "+obj.message;
            return;
        }
  
        this.datosForma.controls["file"].setValue("");
        $("#archivoInput").val("");
        this.upload_file_message = "Documento exitosamente guardado";
        this.transaccionRow[this.transaccionRow.tipoComprobante] = obj.message;
        
        //habilitar el boton de cerrar ventana
        $("#closeWindowButton").removeAttr("disabled");
        
        this.updateListaArchivos();
     
    }
    
    onFileUploadProgress(progress:number) {
        //ir mostrando el progreso de subir el archivo
        this.upload_file_progress = progress.toString() + "%";
    }
    
    onFileChange(event) {
        
        //ocurre cuando se selecciona el archivo
        if(event.target.files.length > 0) {
          const file = event.target.files[0];
          this.datosForma.get("file").setValue(file);
        }
        
        this.tipoArchivoChange();
    }
    
    viewDocument(tipocomprobante:string) {
        
        //si es una nueva transaccion entonces salir
        if(this.transaccionRow == null )
            return;
        
        //mostrar documento en una nueva ventana
        window.open(enviroment.docsURL+this.transaccionRow[tipocomprobante]);
    }
    
    remove(tipocomprobante:string) {
        
        if(!confirm("Seguro que desea eliminar archivo de "+tipocomprobante))
            return;
        
        //guardar el tipo de comprobante que se va a eliminar
        this.transaccionRow.tipoComprobante = tipocomprobante;
        
        //remove file
        this.persistenceService.removeFile(this.transaccionRow.idtransaccion, tipocomprobante).subscribe( message => {this.removeResponse(message);});
    }
    
    removeResponse(res) {
        
        if( res == null || res == undefined ) { alert("error al intentar eliminar archivo"); return;  }
        if( !res.success ) { alert(res.message); return; }
        
        //poner el comprobante en null
        this.transaccionRow[this.transaccionRow.tipoComprobante] = null;
        
        //actualizar la lista de archivos que tiene la transaccion
        this.updateListaArchivos();
        
        //iindicar que la transaccion se pudo realizar exitosamente
        alert("Archivo exitosamente eliminado");
    }
    
    updateListaArchivos() {
        
        //vaciar el arreglo de archivos existentes
        this.listaArchivos = [];
        
        //guardar en el arreglo listaArchivos los docuemntos que tiene la transaccion
        for( var i = 0; i < this.listaTiposArchivo.length; i++ ) {
            
            var item = this.listaTiposArchivo[i];
            
            if( this.transaccionRow[item.val] != null ) {
                item.file = this.transaccionRow[item.val];
                this.listaArchivos.push(item);
            } else
                item.file = null;
        }
        
        //habilitar o deshabilitar el boton de agregar
        this.tipoArchivoChange();
        
    }

}
