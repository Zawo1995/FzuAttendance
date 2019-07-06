import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { C500Component } from './c500.component';

describe('C500Component', () => {
  let component: C500Component;
  let fixture: ComponentFixture<C500Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ C500Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(C500Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
