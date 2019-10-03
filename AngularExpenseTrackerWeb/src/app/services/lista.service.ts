import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { enviroment } from '../model/enviroment';

import {UserService} from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ListaService {

    constructor(private http: HttpClient,private us:UserService) { }
    
    getProveedoresNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/proveedores/nombre",this.us.getHeaders());
    }
    
    getArticulosNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/articulos/nombre",this.us.getHeaders());
    }
    
    getCuentasNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/cuentas/nombre",this.us.getHeaders());
    }
    
    getCategoriasNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/categorias/nombre",this.us.getHeaders());
    }
    
    getGruposNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/grupos/nombre",this.us.getHeaders());
    }
}
