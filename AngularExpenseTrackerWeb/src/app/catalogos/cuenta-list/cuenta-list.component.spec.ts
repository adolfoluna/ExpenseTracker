import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentaListComponent } from './cuenta-list.component';

describe('CuentaListComponent', () => {
  let component: CuentaListComponent;
  let fixture: ComponentFixture<CuentaListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuentaListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuentaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
