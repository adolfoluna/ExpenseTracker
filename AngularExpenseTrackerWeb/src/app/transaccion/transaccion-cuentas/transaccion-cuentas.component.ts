import { Component, OnInit } from '@angular/core';

import { CuentaService } from "../../services/cuenta.service";

@Component({
  selector: 'app-transaccion-cuentas',
  templateUrl: './transaccion-cuentas.component.html',
  styleUrls: ['./transaccion-cuentas.component.css']
})
export class TransaccionCuentasComponent implements OnInit {

  constructor(private cuentaService:CuentaService) { }

  public data = [];
  
  ngOnInit() {
    //se ejecuta cada ves que se cambia el numero de pagina
     //this.paginacionService.cambioPaginaEvent().subscribe(message=> { this.getList(); });
      this.cuentaService.getSummaryList().subscribe(message=> {this.summaryListResponse(message)});
  }
  
  summaryListResponse(res){
      this.data = res;
  }
  
  checkClick(event) {
      
  }

}
