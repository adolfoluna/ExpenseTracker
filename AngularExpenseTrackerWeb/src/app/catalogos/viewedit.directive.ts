import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
    selector: '[view-edit]',
})
export class ViewEditDirective  {
  constructor(public viewContainerRef: ViewContainerRef) { }
}

