import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArrangeManageComponent } from './arrange-manage.component';

describe('ArrangeManageComponent', () => {
  let component: ArrangeManageComponent;
  let fixture: ComponentFixture<ArrangeManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArrangeManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArrangeManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
