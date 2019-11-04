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

import {LoginComponent} from "./login/login.component";

import {AuthGuard} from "./auth.guard";

const routes: Routes = [
    { path: 'login', component: LoginComponent},
    { path: '', component: CuentasResumenComponent,  canActivate:[AuthGuard] },
    { path: 'resumen', component: CuentasResumenComponent, canActivate:[AuthGuard] },      
    { path: 'catalogos/catalogo-view/:catalogo', component: CatalogoViewComponent, canActivate:[AuthGuard] },
    { path: 'transacciones', component: TransaccionViewComponent, canActivate:[AuthGuard] },
    { path: 'transacciones/tipo/:tipo', component: TransaccionViewComponent, canActivate:[AuthGuard] },
    { path: 'transacciones/cuenta/:cuenta', component: TransaccionViewComponent, canActivate:[AuthGuard] },
    { path: 'graficas/categoria', component : CategoriaGraphComponent, canActivate:[AuthGuard] },
    ];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
