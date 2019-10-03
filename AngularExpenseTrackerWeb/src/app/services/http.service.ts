import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { enviroment } from '../model/enviroment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

    constructor(private http: HttpClient) { }
    
    
}
