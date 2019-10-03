import { Component } from '@angular/core';
import * as $ from 'jquery';

import { UserService } from "./services/user.service";
		
//import { RouterOutlet } from '@angular/router';
//import { slideInAnimation } from './animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  // animations: [ slideInAnimation ]
})
export class AppComponent {
  
    title = 'Gastos Entono2';
    
    userLogged:boolean = false;
  
    constructor(private uservice:UserService) {
        
        //tomar por defecto lo que este almacenado en el servicio
        this.userLogged = uservice.isUserLoggedIn();
        
        //escuchar cada vez que entre o salga el usuario
        this.uservice.userStatusChangeEvent().subscribe(res=>this.userStatusChanged());
    }
    
    userStatusChanged() {
        this.userLogged = this.uservice.isUserLoggedIn();
    }
    /*
    getAnimationData(outlet: RouterOutlet) {
        return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
    }*/
}
