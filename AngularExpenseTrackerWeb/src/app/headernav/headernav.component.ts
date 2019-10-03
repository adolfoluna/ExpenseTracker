import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from "../services/user.service";

@Component({
  selector: 'app-headernav',
  templateUrl: './headernav.component.html',
  styleUrls: ['./headernav.component.css']
})
export class HeadernavComponent implements OnInit {

  constructor(private uservice:UserService,private router: Router) { }

  ngOnInit() {
  }
  
  logout() {
      this.uservice.logout().subscribe(res=>this.logoutResponse(res));
  }
  
  logoutResponse(res) {
      console.log("logout response.....");
      this.uservice.setToken(null);
      this.router.navigateByUrl("/login");
  }

}
