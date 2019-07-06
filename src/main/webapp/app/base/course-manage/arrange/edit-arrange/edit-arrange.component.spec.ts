import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditArrangeComponent } from './edit-arrange.component';

describe('EditArrangeComponent', () => {
  let component: EditArrangeComponent;
  let fixture: ComponentFixture<EditArrangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditArrangeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditArrangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
