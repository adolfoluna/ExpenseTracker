import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentaEditComponent } from './cuenta-edit.component';

describe('CuentaEditComponent', () => {
  let component: CuentaEditComponent;
  let fixture: ComponentFixture<CuentaEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuentaEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuentaEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
