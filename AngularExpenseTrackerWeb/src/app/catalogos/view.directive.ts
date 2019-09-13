import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
    selector: '[view-list]',
})
export class ViewDirective  {
  constructor(public viewContainerRef: ViewContainerRef) { }
}

