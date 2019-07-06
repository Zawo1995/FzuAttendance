import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollegeManageComponent } from './college-manage.component';

describe('CollegeManageComponent', () => {
  let component: CollegeManageComponent;
  let fixture: ComponentFixture<CollegeManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollegeManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollegeManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
