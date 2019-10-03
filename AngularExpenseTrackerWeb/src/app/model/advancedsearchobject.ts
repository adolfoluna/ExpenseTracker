
export class AdvancedSearchObject {
    
    limite : number;
    pagina : number;
    catalogo: string;

    orderFields:string[];

    searchGroups: AdvancedSearchGroup[];
    
}

export class AdvancedSearchGroup {

    union: string;
    fields: AdvancedSearchField[];
    
    constructor() {
        this.union = "and";
        this.fields = [];
    }
    
    addField(union:string,fieldName:string,fieldValue:string,comparator:string) {
        
        if(this.fields.length <= 0 )
            union = null;
        
        this.fields.push(new AdvancedSearchField(union,fieldName,fieldValue,comparator));
        
    }
}

export class AdvancedSearchField {
    
    constructor(private union:string,private fieldName:string,private fieldValue:string,private comparator:string) {
        
    }
    
}