import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeneComponent } from './mene.component';

describe('MeneComponent', () => {
  let component: MeneComponent;
  let fixture: ComponentFixture<MeneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
