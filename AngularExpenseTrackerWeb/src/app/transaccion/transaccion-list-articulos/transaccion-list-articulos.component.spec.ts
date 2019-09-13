import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionListArticulosComponent } from './transaccion-list-articulos.component';

describe('TransaccionListArticulosComponent', () => {
  let component: TransaccionListArticulosComponent;
  let fixture: ComponentFixture<TransaccionListArticulosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionListArticulosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionListArticulosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
