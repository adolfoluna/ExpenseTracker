import { Injectable } from '@angular/core';
import { Observable, Subject } from "rxjs";

import { ParametrosBusquedaTransaccion } from "../model/parametros_transaccion";

@Injectable({ providedIn: 'root'})
export class BuscarTransaccionesService {
    
    private subject = new Subject<any>();
    
    constructor() { }
    
    triggerBuscar(item:ParametrosBusquedaTransaccion) {
        this.subject.next(item);
    }
    
    buscarTransaccionesEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
}