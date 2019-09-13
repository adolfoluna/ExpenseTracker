import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionEditArticulosComponent } from './transaccion-edit-articulos.component';

describe('TransaccionEditArticulosComponent', () => {
  let component: TransaccionEditArticulosComponent;
  let fixture: ComponentFixture<TransaccionEditArticulosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionEditArticulosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionEditArticulosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
