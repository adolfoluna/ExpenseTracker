import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaGraphComponent } from './categoria-graph.component';

describe('CategoriaGraphComponent', () => {
  let component: CategoriaGraphComponent;
  let fixture: ComponentFixture<CategoriaGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoriaGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriaGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
