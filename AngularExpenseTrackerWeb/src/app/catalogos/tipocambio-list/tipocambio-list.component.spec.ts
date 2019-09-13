import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TipocambioListComponent } from './tipocambio-list.component';

describe('TipocambioListComponent', () => {
  let component: TipocambioListComponent;
  let fixture: ComponentFixture<TipocambioListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TipocambioListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TipocambioListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
