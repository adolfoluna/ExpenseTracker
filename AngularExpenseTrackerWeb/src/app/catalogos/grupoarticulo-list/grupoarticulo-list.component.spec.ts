import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrupoarticuloListComponent } from './grupoarticulo-list.component';

describe('GrupoarticuloListComponent', () => {
  let component: GrupoarticuloListComponent;
  let fixture: ComponentFixture<GrupoarticuloListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrupoarticuloListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoarticuloListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
