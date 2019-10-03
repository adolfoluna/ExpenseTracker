import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { UserService } from "../services/user.service";
import {Md5} from 'ts-md5/dist/md5';

import { AuthService, GoogleLoginProvider } from 'angular4-social-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForma = this.fb.group({
        user : [""],
        pwd: [""],
        keeploggedin: [false]
    });
    
    errorMessage:string = "";
    temp:string = "";
    
  constructor(private auth_service: AuthService, private userService:UserService, private fb: FormBuilder,private router: Router ) { }

  ngOnInit() {
  }
   
  login() {
      this.errorMessage = "";
      var md5 = new Md5();
      var pwd = md5.appendStr(this.loginForma.controls["pwd"].value).end().toString();
      this.userService.login(this.loginForma.controls["user"].value, pwd).subscribe(response=>this.loginResponse(response));
  }
  
  loginResponse(response:any) {
      
      if( response == null ) {
          this.errorMessage = "Error desconocido";
          return;
      }
      
      if( !response.success ) {
          this.errorMessage = response.message;
          return;
      }
      
      this.userService.setToken(response.data);
      
      this.router.navigateByUrl("/resumen");
  }
  
  loginGoogle() {
      //console.log(this.loginForma.controls["user"].value);
      var platform = GoogleLoginProvider.PROVIDER_ID;
      this.auth_service.signIn(platform).then(response => this.loginGoogleresponse(response) );
      
  }
  
  loginGoogleresponse(response:any) {
      this.temp = response.authToken;
      this.userService.saveToken(response.email, response.authToken).subscribe(res=>this.savetokenResponse(res));
  }
  
  savetokenResponse(res) {
      if( res != null && res.success ) {
          this.userService.setToken(this.temp);
          this.router.navigateByUrl("/resumen");
      }
      else
          this.userService.setToken(null);
  }
  
  signOut(): void {
      this.auth_service.signOut();
      console.log('User signed out.');
    }

}
