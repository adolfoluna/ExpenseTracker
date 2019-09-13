import { Component } from '@angular/core';
import * as $ from 'jquery';

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
  
  
    toggleMenuClick() {
        //console.log("hola");
        //$('#navbarNavDropdown').collapse('hide');
    }
    /*
    getAnimationData(outlet: RouterOutlet) {
        return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
    }*/
}
