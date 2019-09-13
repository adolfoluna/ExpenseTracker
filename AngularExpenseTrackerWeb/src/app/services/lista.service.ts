import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { enviroment } from '../model/enviroment';

@Injectable({
  providedIn: 'root'
})
export class ListaService {

    constructor(private http: HttpClient) { }
    
    getProveedoresNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/proveedores/nombre");
    }
    
    getArticulosNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/articulos/nombre");
    }
    
    getCuentasNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/cuentas/nombre");
    }
    
    getCategoriasNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/categorias/nombre");
    }
    
    getGruposNombre() {
        return this.http.get<any>(enviroment.serviceURL+"lista/grupos/nombre");
    }
}
