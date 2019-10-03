import { Injectable } from '@angular/core';
import { Observable, Subject } from "rxjs";
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { enviroment } from '../model/enviroment';

@Injectable({ providedIn: 'root'})
export class UserService {
    
    private subject = new Subject<any>();
    
    constructor(private http: HttpClient) { 
        
    }
    
    getHeaders() {
        var httpOptions = { headers: new HttpHeaders({"UserToken": localStorage.getItem("token")})};
        return httpOptions;
    }
    
    isUserLoggedIn():boolean {
        if( localStorage.getItem("userLogged") == "true")
            return true;
        else 
            return false;
        //return UserService.userlogged;
    }
    
    login(user:string,pwd:string) {
        localStorage.setItem('user', user);
        return this.http.get<any>(enviroment.serviceURL+"login/authenticate/"+user+"/"+pwd);
    }
    
    logout() {
        return this.http.get<any>(enviroment.serviceURL+"login/logout/"+localStorage.getItem("user"));
    }
    
    saveToken(user:string,token:string) {
        localStorage.setItem('user', user);
        return this.http.get<any>(enviroment.serviceURL+"login/savetoken/"+user+"/"+token);
    }
    
    setToken(token) {
        
        //localStorage.setItem('whatever', 'something');
        
        if( token == null || token == undefined || token.length <= 0 ) {
            localStorage.setItem('token', '')
            localStorage.setItem('userLogged', 'false')
            //UserService.userlogged = false;
            //UserService.token = "";
            this.triggerUserStatusChange();
            return;
        }
        
        //UserService.userlogged = true;
        //UserService.token = token;
        localStorage.setItem('token', token)
        localStorage.setItem('userLogged', 'true')
        this.triggerUserStatusChange();
    }
    
    triggerUserStatusChange() {
        this.subject.next();
    }
    
    userStatusChangeEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
}