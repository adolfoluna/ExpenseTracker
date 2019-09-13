import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionCuentasComponent } from './transaccion-cuentas.component';

describe('TransaccionCuentasComponent', () => {
  let component: TransaccionCuentasComponent;
  let fixture: ComponentFixture<TransaccionCuentasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionCuentasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionCuentasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
