import { Injectable } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { enviroment } from '../model/enviroment';
@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  constructor(private http: HttpClient) { }
  
  getSummaryList() {
      return this.http.get<any>(enviroment.serviceURL+"cuenta/getresumen");
  }
}
