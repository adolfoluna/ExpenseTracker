import { Injectable } from '@angular/core';
import { Observable, Subject } from "rxjs";


@Injectable({ providedIn: 'root'})
export class ViewItemService {
    
    private subject = new Subject<any>();
    
    constructor() { }
    
    triggerViewItem(item:any) {
        this.subject.next(item);
    }
    
    viewItemEvent() : Observable<any> {
        return this.subject.asObservable();
    }
  
}