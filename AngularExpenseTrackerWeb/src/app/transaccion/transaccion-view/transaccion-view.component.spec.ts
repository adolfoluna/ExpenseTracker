import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionViewComponent } from './transaccion-view.component';

describe('TransaccionViewComponent', () => {
  let component: TransaccionViewComponent;
  let fixture: ComponentFixture<TransaccionViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
