import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionEditComponent } from './transaccion-edit.component';

describe('TransaccionEditComponent', () => {
  let component: TransaccionEditComponent;
  let fixture: ComponentFixture<TransaccionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
