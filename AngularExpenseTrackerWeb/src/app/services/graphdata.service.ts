import { Injectable } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { HttpClient, HttpHeaders, HttpEventType } from '@angular/common/http';
import { map } from  'rxjs/operators';

import { enviroment } from '../model/enviroment';
import { SearchObject } from '../model/searchobject';

import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class GraphDataService {

  constructor(private http: HttpClient,private us:UserService) { }
  
  public getTransactionsSumByMonth(limit:number,year:number) {
      return this.http.get<any>(enviroment.serviceURL+"graph/transactions/month/"+limit+"/"+year,this.us.getHeaders());
  }
  
  
}
