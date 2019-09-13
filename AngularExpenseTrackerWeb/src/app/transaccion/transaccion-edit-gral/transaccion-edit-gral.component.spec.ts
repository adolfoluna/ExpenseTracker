import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionEditGralComponent } from './transaccion-edit-gral.component';

describe('TransaccionEditGralComponent', () => {
  let component: TransaccionEditGralComponent;
  let fixture: ComponentFixture<TransaccionEditGralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionEditGralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionEditGralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
