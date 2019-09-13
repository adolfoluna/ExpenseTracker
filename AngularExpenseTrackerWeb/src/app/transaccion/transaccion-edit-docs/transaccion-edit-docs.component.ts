
import { Component, OnInit,Input } from '@angular/core';
import {  FormBuilder } from '@angular/forms';

import { enviroment } from '../../model/enviroment';

import { ParentEditComponent } from "../../catalogos/parent-edit.component";
import { PersistenceService } from '../../services/persistence.service';
import { EditItemService } from '../../services/edit.item.service';

import * as $ from 'jquery';
@Component({
  selector: 'app-transaccion-edit-docs',
  templateUrl: './transaccion-edit-docs.component.html',
  styleUrls: ['./transaccion-edit-docs.component.css']
})
export class TransaccionEditDocsComponent implements OnInit {

    @Input() idtransaccion:number;
    
    upload_file_progress = "0%";
    upload_file_message = "";
    upload_file_tittle = "";
    
    datosForma = this.fb.group( {
        ticket : [""],
        factura: [""],
        complemento : [""],
        voucher : [""],
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
    
    editTransaccionClick(obj) {
        
        //guarda el registro de la transaccion que se esta editando para tomar los valores
        this.transaccionRow = obj;
        
        //habilitar y deshabilitar los botones de ver documento, eliminar, subir archivo
        this.enable_disable_buttons();
    }
    
    uploadFile(tipocomprobante:string) {
        
        //si el registro que se esta editando es nuevo entonces no se pueden agregar documentos
        //a una transaccion que todavia no existe
        if( this.transaccionRow == null ) {
            alert("primero debe escoger la opcion de Guardar para poder guardar archivos");
            return;
        }
        
        if( !(this.datosForma.get(tipocomprobante).value instanceof Object) ) {
            alert("error "+tipocomprobante+" no encontrado o no se ha seleccionado archivo a subir");
            return;
        }
        
        this.upload_file_tittle = tipocomprobante;
        this.upload_file_message = "";
        this.upload_file_progress = "0%";
        
        //deshabilitar boton de cerrar ventana
        $("#closeWindowButton").attr("disabled","disabled");
        
        //$("#progressWindow").modal();
        
        this.transaccionRow.tipoComprobante = tipocomprobante;
        
        const formData = new FormData();
        formData.append('file', this.datosForma.get(tipocomprobante).value);
        this.persistenceService.uploadFile(this.idtransaccion,tipocomprobante,formData).subscribe(message=>{this.uploadFileResponse(message)});
        this.datosForma.controls[tipocomprobante].setValue("");
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
        
        if( obj.success == false ) {
            this.upload_file_message = "error al intentar guardar documento "+obj.message;
            return;
        }
  
        this.datosForma.controls[this.transaccionRow.tipoComprobante].setValue("");
        $("#"+this.transaccionRow.tipoComprobante).val("");
        this.upload_file_message = "Documento exitosamente guardado";
        this.transaccionRow[this.transaccionRow.tipoComprobante] = obj.message;
        
        //habilitar y deshabilitar los botones de ver documento, eliminar, subir archivo
        this.enable_disable_buttons();
        
        //habilitar el boton de cerrar
        $("#closeWindowButton").removeAttr("disabled");
    }
    
    onFileUploadProgress(progress:number) {
        //ir mostrando el progreso de subir el archivo
        this.upload_file_progress = progress.toString() + "%";
    }
    
    onFileChange(event,fieldName:string) {
        
        //ocurre cuando se selecciona el archivo
        if(event.target.files.length > 0) {
          const file = event.target.files[0];
          this.datosForma.get(fieldName).setValue(file);
          
          //actualizar el estado de los botones
          this.enable_disable_buttons();
        }
    }
    
    viewDocument(tipocomprobante:string) {
        
        //si es una nueva transaccion entonces salir
        if(this.transaccionRow == null )
            return;
        
        //mostrar documento en una nueva ventana
        window.open(enviroment.docsURL+this.transaccionRow[tipocomprobante]);
    }
    
    enable_disable_buttons() {
        this.enable_disable_button("ticket");
        this.enable_disable_button("factura");
        this.enable_disable_button("complemento");
        this.enable_disable_button("voucher");
    }
    
    enable_disable_button(field:string) {
        
        //si es una nueva transaccion entonces desabilitar todos los botones
        if( this.transaccionRow == null ) {
            $("#"+field).attr("disabled","disabled");
            $("#button_upload_"+field).attr("disabled","disabled");
            $("#button_view_"+field).attr("disabled","disabled");
            $("#button_remove_"+field).attr("disabled","disabled");
            return;
        }
            
        if( this.transaccionRow[field] ) {
            $("#"+field).attr("disabled","disabled");
            $("#button_upload_"+field).attr("disabled","disabled");
            $("#button_view_"+field).removeAttr("disabled");
            $("#button_remove_"+field).removeAttr("disabled");
            return;
        }
        
        $("#"+field).removeAttr("disabled");
        
        if( (this.datosForma.get(field).value instanceof Object) )
            $("#button_upload_"+field).removeAttr("disabled");
        else 
            $("#button_upload_"+field).attr("disabled","disabled");
        
        $("#button_view_"+field).attr("disabled","disabled");
        $("#button_remove_"+field).attr("disabled","disabled");
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
        
        //actualizar el estatus de los botones
        this.enable_disable_buttons();
        
        //iindicar que la transaccion se pudo realizar exitosamente
        alert("Archivo exitosamente eliminado");
    }

}
