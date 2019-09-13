import { Component, OnInit } from '@angular/core';

import { PaginacionObject } from "../../model/paginacion";

@Component({
  selector: 'app-transaccion-view',
  templateUrl: './transaccion-view.component.html',
  styleUrls: ['./transaccion-view.component.css']
})
export class TransaccionViewComponent implements OnInit {

    paginacionObject:PaginacionObject = new PaginacionObject();
    
    constructor() { }

    ngOnInit() {
    }

}
