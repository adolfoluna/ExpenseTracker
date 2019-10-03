import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { Router } from "@angular/router";
import { UserService } from "./services/user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    
    constructor(private _router: Router,private userService:UserService) {}
    
    canActivate(
          next: ActivatedRouteSnapshot,
          state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        
        if( !this.userService.isUserLoggedIn()) {
            this._router.navigate(['/login']);
            return false;
        }
        
        return true;
  }
  
}
