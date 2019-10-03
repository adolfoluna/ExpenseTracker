import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CuentasResumenComponent } from './cuentas-resumen/cuentas-resumen.component';
import { MonedaListComponent } from './catalogos/moneda-list/moneda-list.component';
import { MonedaEditComponent } from './catalogos/moneda-edit/moneda-edit.component';
import { PaginacionComponent } from './paginacion/paginacion.component';
import { CuentaListComponent } from './catalogos/cuenta-list/cuenta-list.component';
import { CuentaEditComponent } from './catalogos/cuenta-edit/cuenta-edit.component';
import { TipocambioListComponent } from './catalogos/tipocambio-list/tipocambio-list.component';
import { TipocambioEditComponent } from './catalogos/tipocambio-edit/tipocambio-edit.component';
import { ProveedorListComponent } from './catalogos/proveedor-list/proveedor-list.component';
import { ProveedorEditComponent } from './catalogos/proveedor-edit/proveedor-edit.component';
import { CatalogoViewComponent } from './catalogos/catalogo-view/catalogo-view.component';
import { ViewDirective } from './catalogos/view.directive';
import { ViewEditDirective } from "./catalogos/viewedit.directive";

import { TransaccionEditComponent } from './transaccion/transaccion-edit/transaccion-edit.component';
import { TransaccionViewComponent } from './transaccion/transaccion-view/transaccion-view.component';
import { TransaccionListComponent } from './transaccion/transaccion-list/transaccion-list.component';
import { TransaccionCuentasComponent } from './transaccion/transaccion-cuentas/transaccion-cuentas.component';
import { TransaccionParametrosComponent } from './transaccion/transaccion-parametros/transaccion-parametros.component';

import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgDatepickerModule } from 'ng2-datepicker';

import { TransaccionEditArticulosComponent } from './transaccion/transaccion-edit-articulos/transaccion-edit-articulos.component';
import { TransaccionEditDocsComponent } from './transaccion/transaccion-edit-docs/transaccion-edit-docs.component';
import { TransaccionEditGralComponent } from './transaccion/transaccion-edit-gral/transaccion-edit-gral.component';
import { TransaccionViewDetailComponent } from './transaccion/transaccion-view-detail/transaccion-view-detail.component';
import { TransaccionListArticulosComponent } from './transaccion/transaccion-list-articulos/transaccion-list-articulos.component';
import { CategoriaListComponent } from './catalogos/categoria-list/categoria-list.component';
import { CategoriaEditComponent } from './catalogos/categoria-edit/categoria-edit.component';
import { ArticuloEditComponent } from './catalogos/articulo-edit/articulo-edit.component';
import { ArticuloListComponent } from './catalogos/articulo-list/articulo-list.component';
import { GrupoListComponent } from './catalogos/grupo-list/grupo-list.component';
import { GrupoEditComponent } from './catalogos/grupo-edit/grupo-edit.component';
import { GrupoarticuloListComponent } from './catalogos/grupoarticulo-list/grupoarticulo-list.component';
import { GrupoarticuloEditComponent } from './catalogos/grupoarticulo-edit/grupoarticulo-edit.component';

import { TruncatePipe } from './model/truncatepipe';
import { CategoriaGraphComponent } from './graficas/categoria-graph/categoria-graph.component';
import { LoginComponent } from './login/login.component';
import { HeadernavComponent } from './headernav/headernav.component';

import { BehaviorSubject } from "rxjs";
//Importing social login module and google login provider.
import { SocialLoginModule, AuthServiceConfig, GoogleLoginProvider } from 'angular4-social-login';

const google_oauth_client_id:string = '172719573787-an7rev6thkjiu39jskup4akelu4tv2j5.apps.googleusercontent.com';
let config = new AuthServiceConfig([
                                    {
                                      id: GoogleLoginProvider.PROVIDER_ID,
                                      provider: new GoogleLoginProvider(google_oauth_client_id)
                                    }
                                  ]);


@NgModule({
  declarations: [
    AppComponent,
    CuentasResumenComponent,
    MonedaListComponent,
    MonedaEditComponent,
    PaginacionComponent,
    CuentaListComponent,
    CuentaEditComponent,
    TipocambioListComponent,
    TipocambioEditComponent,
    ProveedorListComponent,
    ProveedorEditComponent,
    CatalogoViewComponent,
    ViewDirective,
    ViewEditDirective,
    TransaccionListComponent,
    TransaccionEditComponent,
    TransaccionViewComponent,
    TransaccionCuentasComponent,
    TransaccionParametrosComponent,
    TransaccionEditArticulosComponent,
    TransaccionEditDocsComponent,
    TransaccionEditGralComponent,
    TransaccionViewDetailComponent,
    TransaccionListArticulosComponent,
    CategoriaListComponent,
    CategoriaEditComponent,
    ArticuloEditComponent,
    ArticuloListComponent,
    GrupoListComponent,
    GrupoEditComponent,
    GrupoarticuloListComponent,
    GrupoarticuloEditComponent,
    TruncatePipe,
    CategoriaGraphComponent,
    LoginComponent,
    HeadernavComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgMultiSelectDropDownModule,
    NgDatepickerModule,
    SocialLoginModule.initialize(config),
  ],
  
  entryComponents : [
        MonedaListComponent,
        MonedaEditComponent,
        PaginacionComponent,
        CuentaListComponent,
        CuentaEditComponent,
        TipocambioListComponent,
        TipocambioEditComponent,
        ProveedorListComponent,
        ProveedorEditComponent,
        TransaccionListComponent,
        TransaccionEditComponent,
        CategoriaListComponent,
        CategoriaEditComponent,
        ArticuloListComponent,
        ArticuloEditComponent,
        GrupoListComponent,
        GrupoEditComponent,
        GrupoarticuloListComponent,
        GrupoarticuloEditComponent,
        ],
  providers: [  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}