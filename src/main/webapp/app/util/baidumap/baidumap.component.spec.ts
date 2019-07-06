import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaidumapComponent } from './baidumap.component';

describe('BaidumapComponent', () => {
  let component: BaidumapComponent;
  let fixture: ComponentFixture<BaidumapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaidumapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaidumapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
