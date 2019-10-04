import { Injectable } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { enviroment } from '../model/enviroment';
import {UserService} from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  constructor(private http: HttpClient,private us:UserService) { }
  
  getSummaryList() {
      return this.http.get<any>(enviroment.serviceURL+"cuenta/getresumen",this.us.getHeaders());
  }
}
