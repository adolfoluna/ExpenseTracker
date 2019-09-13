import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentasResumenComponent } from './cuentas-resumen.component';

describe('CuentasResumenComponent', () => {
  let component: CuentasResumenComponent;
  let fixture: ComponentFixture<CuentasResumenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuentasResumenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuentasResumenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
