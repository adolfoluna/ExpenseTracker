import { Injectable } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { HttpClient, HttpHeaders, HttpEventType } from '@angular/common/http';
import { map } from  'rxjs/operators';

import { enviroment } from '../model/enviroment';
import { SearchObject } from '../model/searchobject';

@Injectable({
  providedIn: 'root'
})
export class PersistenceService {

  constructor(private http: HttpClient) { }
  
  public getList(search:SearchObject) {
      return this.http.post<any>(enviroment.serviceURL+"persistence/listar",search);
  }
  
  public getObject(search:SearchObject) {
      return this.http.post<any>(enviroment.serviceURL+"persistence/obtener",search); 
  }
  
  public setObject(catalogo:string,objeto:any) {
      return this.http.post<any>(enviroment.serviceURL+"persistence/guardar/"+catalogo,objeto);
  }
  
  public removeObject(search:SearchObject) {
      return this.http.post<any>(enviroment.serviceURL+"persistence/remover",search);
  }
  
  public uploadFile(idtransaccion:number,tipo:string,data:any) {
      return this.http.post<any>(enviroment.serviceURL+"archivo/subir/"+idtransaccion+"/"+tipo,data, {reportProgress: true,observe: 'events'})
      .pipe(map((event) => {

          switch (event.type) {

            case HttpEventType.UploadProgress:
              const progress = Math.round(100 * event.loaded / event.total);
              return { status: 'progress', message: progress };

            case HttpEventType.Response:
              return event.body;
              
            default:
              return { status:"event", message: "Unhandled event: ${event.type}" };
          }
        })
        );
  }
  
  public removeFile(idtransaccion:number,tipo:string) {
      return this.http.get<any>(enviroment.serviceURL+"archivo/eliminar/"+idtransaccion+"/"+tipo);
  }
}
