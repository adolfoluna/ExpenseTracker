import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionParametrosComponent } from './transaccion-parametros.component';

describe('TransaccionParametrosComponent', () => {
  let component: TransaccionParametrosComponent;
  let fixture: ComponentFixture<TransaccionParametrosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionParametrosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionParametrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
