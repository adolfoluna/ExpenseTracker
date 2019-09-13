import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TipocambioEditComponent } from './tipocambio-edit.component';

describe('TipocambioEditComponent', () => {
  let component: TipocambioEditComponent;
  let fixture: ComponentFixture<TipocambioEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TipocambioEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TipocambioEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
