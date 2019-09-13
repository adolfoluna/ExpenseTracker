
export class PaginacionObject {
    
    limite : number;
    pagina : number;
    numResultados: number;
    numeroPaginas: number;
    ruta:string;

    paginas : number[];

    rutaPrimero: string;
    rutaAnterior: string;
    rutaSiguiente: string;
    rutaUltimo: string;
    
    //paginas: PaginacionItem[];

    constructor() {
        this.limite = 10;
        this.pagina = 0;
        this.numResultados = 0;
        this.numeroPaginas = 0;
        this.paginas = [];
    }
    
}

export class PaginacionItem {
    
    pagina : number;
    ruta: string;
    activo:boolean;

    constructor(pagina:number,ruta:string,activo:boolean) {
        this.pagina = pagina;
        this.ruta = ruta;
        this.activo = activo;
    }
    
}