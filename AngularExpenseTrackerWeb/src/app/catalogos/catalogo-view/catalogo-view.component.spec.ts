import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoViewComponent } from './catalogo-view.component';

describe('CatalogoViewComponent', () => {
  let component: CatalogoViewComponent;
  let fixture: ComponentFixture<CatalogoViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CatalogoViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CatalogoViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
