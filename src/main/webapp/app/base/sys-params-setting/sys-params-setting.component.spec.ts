import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SysParamsSettingComponent } from './sys-params-setting.component';

describe('SysParamsSettingComponent', () => {
  let component: SysParamsSettingComponent;
  let fixture: ComponentFixture<SysParamsSettingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SysParamsSettingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SysParamsSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
