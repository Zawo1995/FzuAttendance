import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PowerSettingComponent } from './power-setting.component';

describe('PowerSettingComponent', () => {
  let component: PowerSettingComponent;
  let fixture: ComponentFixture<PowerSettingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PowerSettingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PowerSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
