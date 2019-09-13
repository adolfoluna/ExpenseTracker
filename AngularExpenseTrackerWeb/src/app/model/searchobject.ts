
import { AdvancedSearchGroup } from "./advancedsearchobject";

export class SearchObject {
    
    limite : number;
    pagina : number;
    catalogo: string;

    orderFields:string[];

    searchFields: SearchField[];

    advancedSearchGroups: AdvancedSearchGroup[];

    constructor() {
        this.orderFields = [];
        this.searchFields = [];
        this.advancedSearchGroups = [];
        this.limite = 0;
        this.pagina = 0;
        this.catalogo = "";
    }
    
    addSearchField(fieldName:string,value:string) {
        this.searchFields.push(new SearchField(fieldName,value));
    }
    
    addAdvancedSearchGroup(advancedSearchGroup:AdvancedSearchGroup) {
        this.advancedSearchGroups.push(advancedSearchGroup);
    }
    
    addAndField(fieldName:string,value:string,comparator:string) {
        var aux = new AdvancedSearchGroup();
        aux.addField(null,fieldName,value,comparator);
        this.addAdvancedSearchGroup(aux);
    }
    
}

export class SearchField {

    constructor(private fieldName:string,private value:string) {
    }
}