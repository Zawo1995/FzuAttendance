import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {C404Component} from "./base/c404/c404.component";
import {C500Component} from "./base/c500/c500.component";
import {C403Component} from "./base/c403/c403.component";
import {ForgetPasswordComponent} from "./base/cover/forget-password/forget-password.component";
import {SchoolManagerComponent} from "./base/school-manager/school-manager.component";
import {CollegeManageComponent} from './base/college-manage/college-manage.component';
import {CourseManageComponent} from './base/course-manage/course-manage.component';
import {SysParamsSettingComponent} from './base/sys-params-setting/sys-params-setting.component';
import {RoleManageComponent} from './base/role-manage/role-manage.component';
import {UserManageComponent} from './base/user-manage/user-manage.component';
import {MenuManageComponent} from './base/menu-manage/menu-manage.component';
import {MeneComponent} from './base/mene/mene.component';
import {LoginComponent} from './base/cover/login/login.component';
import {CanAuthService} from './service/can-auth.service';
import {RegisterComponent} from './base/cover/register/register.component';
import {CoverComponent} from './base/cover/cover.component';
import {LockComponent} from './base/cover/lock/lock.component';
import {ArrangeManageComponent} from './base/arrange-manage/arrange-manage.component';
import {SignManageComponent} from './base/sign-manage/sign-manage.component';
import {DataDicComponent} from './base/data-dic/data-dic.component';


const appRoute: Routes = [

  {path: '', component: LoginComponent},
  {
    path: 'cover', component: CoverComponent, children: [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'lock', component: LockComponent},
    {path: 'forget-password', component: ForgetPasswordComponent},
  ]
  },
  {
    path: 'menus', component: MeneComponent, canActivate: [CanAuthService], data: {breadcrumb: 'home'}, children: [
    {path: 'user-manage', component: UserManageComponent, canActivate: [CanAuthService], data: {breadcrumb: '用户管理'}},
    {path: 'role-manage', component: RoleManageComponent, canActivate: [CanAuthService], data: {breadcrumb: '角色管理'}},
    {path: 'school-manage', component: SchoolManagerComponent, canActivate: [CanAuthService], data: {breadcrumb: '学校管理'}},
    {path: 'college-manage', component: CollegeManageComponent, canActivate: [CanAuthService], data: {breadcrumb: '学院管理'}},
    {path: 'course-manage', component: CourseManageComponent, canActivate: [CanAuthService], data: {breadcrumb: '班课管理'}},
    {path: 'arr-manage', component: ArrangeManageComponent, canActivate: [CanAuthService], data: {breadcrumb:  '排课管理'}},
    {path: 'att-manage', component: SignManageComponent, canActivate: [CanAuthService], data: {breadcrumb:  '考勤管理'}},
    {path: 'sys-params-setting', component: SysParamsSettingComponent, canActivate: [CanAuthService], data: {breadcrumb: '系统参数设置'}},
    {path: 'data-dic', component: DataDicComponent, canActivate: [CanAuthService], data: {breadcrumb: '数据字典管理'}},
    {path: 'menu-manage', component: MenuManageComponent, canActivate: [CanAuthService], data: {breadcrumb: '菜单管理'}},
  ]
  },
  {path: 'c500', component: C500Component},
  {path: 'c403', component: C403Component},
  {path: 'c404', component: C404Component},
  {path: '**', component: C404Component},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoute, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
