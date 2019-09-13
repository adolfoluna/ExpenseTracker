import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransaccionEditDocsComponent } from './transaccion-edit-docs.component';

describe('TransaccionEditDocsComponent', () => {
  let component: TransaccionEditDocsComponent;
  let fixture: ComponentFixture<TransaccionEditDocsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransaccionEditDocsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransaccionEditDocsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
