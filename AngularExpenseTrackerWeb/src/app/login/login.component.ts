import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { UserService } from "../services/user.service";
import {Md5} from 'ts-md5/dist/md5';

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
    loginDisabled:boolean = false;
    
    constructor(private userService:UserService, private fb: FormBuilder,private router: Router ) { }

    ngOnInit() {
    }
   
    login() {
        
        this.loginDisabled = true;
      
      //limpiar mensaje de error si es que existe
      this.errorMessage = "";

      //cifrar la clave con MD5
      var md5 = new Md5();
      var pwd = md5.appendStr(this.loginForma.controls["pwd"].value).end().toString();
      
      //hacer el request al servidor para iniciar sesion
      this.userService.loginRequest(this.loginForma.controls["user"].value, pwd).subscribe(response=>this.loginResponse(response));
    }
  
    loginResponse(response:any) {
      
      if( response == null ) {
          this.errorMessage = "Error desconocido";
          this.loginDisabled = false;
          return;
      }
      
      if( !response.success ) {
          this.errorMessage = response.message;
          this.loginDisabled = false;
          return;
      }
      
      //avisar que el login fue exitoso para que se actualicen las variables
      this.userService.loginSuccessful(response);
      
      //redirigir pagina a resumen
      this.router.navigateByUrl("/resumen");
    }
  
    signOut(): void {
     
    }

}
