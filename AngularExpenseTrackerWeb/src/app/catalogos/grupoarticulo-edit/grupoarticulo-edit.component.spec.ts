import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrupoarticuloEditComponent } from './grupoarticulo-edit.component';

describe('GrupoarticuloEditComponent', () => {
  let component: GrupoarticuloEditComponent;
  let fixture: ComponentFixture<GrupoarticuloEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrupoarticuloEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoarticuloEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
