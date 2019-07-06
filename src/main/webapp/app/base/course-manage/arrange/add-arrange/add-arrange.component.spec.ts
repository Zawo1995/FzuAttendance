import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddArrangeComponent } from './add-arrange.component';

describe('AddArrangeComponent', () => {
  let component: AddArrangeComponent;
  let fixture: ComponentFixture<AddArrangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddArrangeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddArrangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
