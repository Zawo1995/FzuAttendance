import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { C403Component } from './c403.component';

describe('C403Component', () => {
  let component: C403Component;
  let fixture: ComponentFixture<C403Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ C403Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(C403Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
