import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionViewDetailComponent } from './transaccion-view-detail.component';

describe('TransaccionViewDetailComponent', () => {
  let component: TransaccionViewDetailComponent;
  let fixture: ComponentFixture<TransaccionViewDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionViewDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionViewDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
