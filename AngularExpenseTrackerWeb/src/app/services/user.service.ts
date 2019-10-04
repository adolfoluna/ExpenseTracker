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
        var httpOptions = { headers: new HttpHeaders({"UserToken": localStorage.getItem("token"),"UserName":localStorage.getItem("user")})};
        return httpOptions;
    }
    
    isUserLoggedIn():boolean {
        if( localStorage.getItem("userLogged") != null && localStorage.getItem("userLogged") == "true")
            return true;
        else 
            return false;
    }
    
    getRoleNumber():number {
        
        if( localStorage.getItem("role") == null || localStorage.getItem("role") == undefined )
            return 0;
        
        return parseInt( localStorage.getItem("role") );
    }
    
    loginRequest(user:string,pwd:string) {
        return this.http.get<any>(enviroment.serviceURL+"login/authenticate/"+user+"/"+pwd);
    }
    
    loginExternalRequest(user:string,token:string) {
        localStorage.setItem('user', user);
        return this.http.get<any>(enviroment.serviceURL+"login/authenticate_external/"+user+"/"+token);
    }
    
    loginSuccessful(response:any) {
        localStorage.setItem("user",response.data.usuario);
        localStorage.setItem("token",response.data.token);
        localStorage.setItem("role", response.data.rolnum.toString());
        localStorage.setItem('userLogged', 'true');//indicar que el usuario ya esta firmado
        this.triggerUserStatusChange();//avisar que el estatus del usuario cambio
    }
    
    logout() {
        var user = localStorage.getItem("user");
        localStorage.clear();
        this.triggerUserStatusChange();//avisar que el estatus del usuario cambio
        return this.http.get<any>(enviroment.serviceURL+"login/logout/"+user);
    }
    
    triggerUserStatusChange() {
        this.subject.next();
    }
    
    userStatusChangeEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
}