import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {
  FooterComponent, MeauListComponent, PageMenusComponent,
} from "../page-manage/page-manage";
import {AppRoutingModule} from "./app-routing.module";
import {C404Component} from './base/c404/c404.component';
import {HttpServiceProvider} from "./service/service-http";
import {HttpClientModule} from "@angular/common/http";
import {NgZorroAntdModule, /* NZ_I18N, zh_CN*/} from 'ng-zorro-antd';
import {C500Component} from './base/c500/c500.component';
import {C403Component} from './base/c403/c403.component';
import {ForgetPasswordComponent} from './base/cover/forget-password/forget-password.component';
import {EditUserComponent} from './base/user-manage/edit-user/edit-user.component';
import {SchoolManagerComponent} from './base/school-manager/school-manager.component';
import {RoleManageComponent} from './base/role-manage/role-manage.component';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AddSchoolComponent} from './base/school-manager/add-school/add-school.component';
import {EditSchoolComponent} from './base/school-manager/edit-school/edit-school.component';
import {CollegeManageComponent} from './base/college-manage/college-manage.component';
import {AddCollegeComponent} from './base/college-manage/add-college/add-college.component';
import {EditCollegeComponent} from './base/college-manage/edit-college/edit-college.component';
import {OrderByPipe} from './pipe/order-by.pipe';
import {RestTimeComponent} from './base/school-manager/rest-time/rest-time.component';
import {CourseManageComponent} from './base/course-manage/course-manage.component';
import {AddCourseComponent} from './base/course-manage/add-course/add-course.component';
import {EditCourseComponent} from './base/course-manage/edit-course/edit-course.component';
import {ArrangeComponent} from './base/course-manage/arrange/arrange.component';
import {DayNumPipe} from './pipe/day-num.pipe';
import {AddArrangeComponent} from './base/course-manage/arrange/add-arrange/add-arrange.component';
import {BaiduMapModule} from  'angular2-baidu-map'
import {BaidumapComponent} from './util/baidumap/baidumap.component';
import {EditArrangeComponent} from './base/course-manage/arrange/edit-arrange/edit-arrange.component';
import {SysParamsSettingComponent} from './base/sys-params-setting/sys-params-setting.component';
import {AddRoleComponent} from './base/role-manage/add-role/add-role.component';
import {UserManageComponent} from './base/user-manage/user-manage.component';
import {registerLocaleData} from '@angular/common';
import zh from '@angular/common/locales/zh';
import {AddUserComponent} from './base/user-manage/add-user/add-user.component';
import {AccountManageComponent} from './base/account-manage/account-manage.component';
import {AddAccountComponent} from './base/account-manage/add-account/add-account.component';
import {MenuManageComponent} from './base/menu-manage/menu-manage.component';
import {EditMenuComponent} from './base/menu-manage/edit-menu/edit-menu.component';
import {IconSelectComponent} from './base/icon-select/icon-select.component';
import {PowerSettingComponent} from './base/role-manage/power-setting/power-setting.component';
import {MeneComponent} from './base/mene/mene.component';
import {LoginComponent} from './base/cover/login/login.component';
import {LocalStorageProvider} from './service/local-storage';
import {CanAuthService} from './service/can-auth.service';
import {RegisterComponent} from './base/cover/register/register.component';
import {CoverComponent} from './base/cover/cover.component';
import {NZ_NOTIFICATION_CONFIG} from 'ng-zorro-antd/notification';
import {LockComponent} from './base/cover/lock/lock.component';
import {AlertService} from './service/alert.service';
import { ArrangeManageComponent } from './base/arrange-manage/arrange-manage.component';
import { SignManageComponent } from './base/sign-manage/sign-manage.component';
import { DataDicComponent } from './base/data-dic/data-dic.component';
import { SignDetailComponent } from './base/sign-manage/sign-detail/sign-detail.component';
registerLocaleData(zh);

export let layer;
@NgModule({
  declarations: [
    AppComponent,
    PageMenusComponent,
    FooterComponent,
    MeauListComponent,
    DayNumPipe,
    OrderByPipe,
    C404Component,
    C500Component,
    C403Component,
    ForgetPasswordComponent,
    EditUserComponent,
    SchoolManagerComponent,
    RoleManageComponent,
    AddSchoolComponent,
    EditSchoolComponent,
    CollegeManageComponent,
    AddCollegeComponent,
    EditCollegeComponent,
    RestTimeComponent,
    CourseManageComponent,
    AddCourseComponent,
    EditCourseComponent,
    ArrangeComponent,
    AddArrangeComponent,
    BaidumapComponent,
    EditArrangeComponent,
    SysParamsSettingComponent,
    AddRoleComponent,
    UserManageComponent,
    AddUserComponent,
    AccountManageComponent,
    AddAccountComponent,
    MenuManageComponent,
    EditMenuComponent,
    IconSelectComponent,
    PowerSettingComponent,
    MeneComponent,
    LoginComponent,
    RegisterComponent,
    CoverComponent,
    LockComponent,
    ArrangeManageComponent,
    SignManageComponent,
    DataDicComponent,
    SignDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgZorroAntdModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    BaiduMapModule.forRoot({
      ak: 'zYEW7hvnqCRI0oWIau6K0GcC0M4nyUaQ'
    })
  ],
  providers: [
    CanAuthService,
    LocalStorageProvider,
    HttpServiceProvider,
    FormBuilder,
    OrderByPipe,
    DayNumPipe,
    AlertService,
    {
      provide: NZ_NOTIFICATION_CONFIG, useValue: {nzMaxStack: 1},
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    AddSchoolComponent,
    EditSchoolComponent,
    AddCollegeComponent,
    EditCollegeComponent,
    RestTimeComponent,
    AddCourseComponent,
    EditCourseComponent,
    ArrangeComponent,
    AddArrangeComponent,
    BaidumapComponent,
    EditArrangeComponent,
    AddRoleComponent,
    AddUserComponent,
    EditUserComponent,
    AccountManageComponent,
    AddAccountComponent,
    EditMenuComponent,
    IconSelectComponent,
    PowerSettingComponent,
    SignDetailComponent,
  ]
})
export class AppModule {
  constructor() {

  }
}
