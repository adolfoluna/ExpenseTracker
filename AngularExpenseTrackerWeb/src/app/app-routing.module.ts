import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CuentasResumenComponent } from './cuentas-resumen/cuentas-resumen.component';
import { MonedaListComponent } from './catalogos/moneda-list/moneda-list.component';
import { MonedaEditComponent } from './catalogos/moneda-edit/moneda-edit.component';
import { CuentaListComponent } from './catalogos/cuenta-list/cuenta-list.component';
import { CuentaEditComponent } from './catalogos/cuenta-edit/cuenta-edit.component';
import { TipocambioListComponent } from './catalogos/tipocambio-list/tipocambio-list.component';
import { TipocambioEditComponent } from './catalogos/tipocambio-edit/tipocambio-edit.component';
import { ProveedorListComponent } from './catalogos/proveedor-list/proveedor-list.component';
import { ProveedorEditComponent } from "./catalogos/proveedor-edit/proveedor-edit.component";
import { CatalogoViewComponent } from './catalogos/catalogo-view/catalogo-view.component';
import { TransaccionListComponent } from './transaccion/transaccion-list/transaccion-list.component';
import { TransaccionViewComponent } from "./transaccion/transaccion-view/transaccion-view.component";

import { CategoriaGraphComponent } from "./graficas/categoria-graph/categoria-graph.component";

const routes: Routes = [
    { path: '', component: CuentasResumenComponent },      
    { path: 'catalogos/catalogo-view/:catalogo', component: CatalogoViewComponent },
    { path: 'transacciones', component: TransaccionViewComponent },
    { path: 'transacciones/tipo/:tipo', component: TransaccionViewComponent },
    { path: 'transacciones/cuenta/:cuenta', component: TransaccionViewComponent },
    { path: 'graficas/categoria', component : CategoriaGraphComponent },
    ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
