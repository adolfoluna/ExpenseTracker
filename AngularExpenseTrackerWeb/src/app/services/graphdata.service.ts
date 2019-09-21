import { Injectable } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { HttpClient, HttpHeaders, HttpEventType } from '@angular/common/http';
import { map } from  'rxjs/operators';

import { enviroment } from '../model/enviroment';
import { SearchObject } from '../model/searchobject';

@Injectable({
  providedIn: 'root'
})
export class GraphDataService {

  constructor(private http: HttpClient) { }
  
  public getTransactionsSumByMonth(limit:number,year:number) {
      return this.http.get<any>(enviroment.serviceURL+"graph/transactions/month/"+limit+"/"+year);
  }
  
  
}
