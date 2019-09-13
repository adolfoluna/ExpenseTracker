import { Injectable } from '@angular/core';
import { Observable, Subject } from "rxjs";


@Injectable({ providedIn: 'root'})
export class EditItemService {
    
    private subject = new Subject<any>();
    
    constructor() { }
    
    triggerEditItem(item:any,catalogo:string) {
        
         if( item != null  )
            item._catalogo = catalogo;
      
        this.subject.next(item);
   
    }
    
    editItemEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
}