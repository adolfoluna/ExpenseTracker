import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from "../services/user.service";

@Component({
  selector: 'app-headernav',
  templateUrl: './headernav.component.html',
  styleUrls: ['./headernav.component.css']
})
export class HeadernavComponent implements OnInit {

    rolnum:number = 0;
    
    constructor(private uservice:UserService,private router: Router) { }

    ngOnInit() {
        this.rolnum = this.uservice.getRoleNumber();
    }
  
    logout() {
        this.uservice.logout().subscribe(res=>this.logoutResponse(res));
    }
  
    logoutResponse(res) {
        this.router.navigateByUrl("/login");
    }

}
