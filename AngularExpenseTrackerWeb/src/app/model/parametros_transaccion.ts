
    export class ParametrosBusquedaTransaccion {
    
        idtransaccion: string;
        proveedores : ParametrosBusquedaTransaccionItem[];
        articulos : ParametrosBusquedaTransaccionItem[];
        comprobantes : ParametrosBusquedaTransaccionItem[];
        cuentas: ParametrosBusquedaTransaccionItem[];
        minfecha : string;
        maxfecha : string;
    
        constructor() {
            this.idtransaccion = "";
            this.proveedores = [];
            this.articulos = [];
            this.comprobantes = [];
            this.cuentas = [];
            this.minfecha = null;
            this.maxfecha = null;
        }
    
    }

    export class ParametrosBusquedaTransaccionItem {
        id : string;
        name : string;
    }