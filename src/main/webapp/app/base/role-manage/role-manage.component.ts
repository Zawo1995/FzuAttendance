import {Component, OnInit, TemplateRef} from '@angular/core';
import zh from '@angular/common/locales/zh';
import {registerLocaleData} from '@angular/common';
import {HttpServiceProvider} from '../../service/service-http';
import {NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {AddRoleComponent} from './add-role/add-role.component';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {PowerSettingComponent} from './power-setting/power-setting.component';

@Component({
  selector: 'app-role',
  templateUrl: './role-manage.component.html',
  styleUrls: ['./role-manage.component.css'],
})
export class RoleManageComponent implements OnInit {

  listOfDisplayData = [];
  listOfAllData = [];
  isAllDisplayDataChecked = false;
  mapOfCheckedId: { [key: number]: boolean } = {};
  numberOfChecked = 0;
  isIndeterminate = false;
  loading = true;
  buttonLoading = false;

  searchName: string;
  searchValue: string = '';
  searchRoleType = 1;

  orderByArray = [];
  RoleColor: { [key: string]: {} } = {
    '超级管理员': {abbr: '超', color: 'limegreen'},
    '管理员': {abbr: '管', color: 'green'},
    '教师': {abbr: '师', color: 'cyan'},
    '学生': {abbr: '生', color: 'blue'},
  }

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,
              private notice: NzNotificationService) {
  }

  ngOnInit() {
    this.queryList();
  }

  toggleType(type: number) {
    this.searchRoleType = type;
    this.queryList();
  }

  queryList() {
    let params = {};
    if (this.searchName != null) {
      params[this.searchName] = this.searchValue;
    }
    params['isDefault'] = this.searchRoleType;
    console.debug(params);
    this.loading = true;
    this.http.get('role', params).then((data) => {
      if (data.success) {
        this.listOfAllData = data.data;
      } else {
        this.message.error(data.message);
      }
      this.loading = false;
      console.debug(data);
    }, (e) => {
      console.debug(e);
    })
  }

  refresh() {
    this.searchValue = null;
    this.searchName = null;
    this.refreshStatus();
    this.queryList();
  }

  refreshStatus(): void {
    this.isAllDisplayDataChecked = this.listOfDisplayData
      .filter(item => !item.disabled)
      .every(item => this.mapOfCheckedId[item.id]);
    this.isIndeterminate =
      this.listOfDisplayData.filter(item => !item.disabled).some(item => this.mapOfCheckedId[item.id]) &&
      !this.isAllDisplayDataChecked;
    this.numberOfChecked = this.listOfAllData.filter(item => this.mapOfCheckedId[item.id]).length;
  }

  checkAll(value: boolean): void {
    this.listOfDisplayData.filter(item => !item.disabled).forEach(item => {
      if (item.id > 0) {//只加入允许修改删除的角色名
        this.mapOfCheckedId[item.id] = value;
      }
    });
    this.refreshStatus();
  }

  currentPageDataChange($event: Array<{ id: number; name: string; age: number; address: string }>) {
    this.listOfDisplayData = $event;
    this.refreshStatus();
  }

  sort(name, value) {
    console.debug(value);
    if (value == 'ascend') {
      this.orderByArray = ['+' + name];
    } else if (value == 'descend') {
      this.orderByArray = ['-' + name];
    } else {
      this.orderByArray = [];
      this.queryList();
    }

  }

  newAddRoleId;

  addRole(template: TemplateRef<{}>) {
    let modal = this.modalService.create({
      nzTitle: '新增信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddRoleComponent,
    });
    modal.afterClose.subscribe((d) => {
      this.queryList();
      if (d != null) {
        this.newAddRoleId = d;
        this.notice.template(template);
      }
    })
  }

  deleteRole(id: number) {
    let list = [];
    list.push(id);
    this.modalService.confirm({
      nzTitle: '<i>你真的要删除这个角色信息</i>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        this.http.delete('role', {ids: list.join()}).then((d) => {
          if (!d.success) {
            this.message.error(d.message)
          } else {
            this.message.success(d.message);
            this.queryList();
          }
        }, e => {
          console.error(e);
          this.message.error('服务器异常');
        });
      },
      nzCancelText: '取消',
      nzOnCancel: () => this.message.info('取消删除')
    })
  }

  deleteRoles() {
    if (this.numberOfChecked == 0) {
      this.message.warning('请至少选择一条信息');
      return;
    }
    this.buttonLoading = true;
    this.modalService.confirm({
      nzTitle: '<i>批量删除</i>',
      nzContent: '<b style="color: red;">真的删除这些信息吗?</b>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        let list = this.getCheckedData();
        this.http.delete('role', {ids: list.join()}).then((d) => {
          this.buttonLoading = false;
          if (!d.success) {
            this.message.error(d.message)
          } else {
            this.message.success(d.message);
            this.queryList();
          }
        }, e => {
          console.error(e);
          this.message.error('服务器异常');
        });
      },
      nzCancelText: '取消',
      nzOnCancel: () => {
        this.message.info('取消删除');
        this.buttonLoading = false;
      }
    })
  }

  getCheckedData(): any {
    let list = [];
    this.listOfAllData.forEach(item => {
      if (this.mapOfCheckedId[item.id] == true) {
        list.push(item.id);
        this.mapOfCheckedId[item.id] = false;
      }
    });
    return list;
  }

  editRole(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改角色【' + data.name + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddRoleComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((d) => {
      if (d) {
        this.queryList();
      }
    })
  }

  actionSetting(i) {//权限设置
    let data: any = {
      id: i.id
    }
    let modal = this.modalService.create({
      nzTitle: '角色-' + i.name + '/权限设置',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: PowerSettingComponent,
      nzComponentParams: data
    });
    modal.afterClose.subscribe(result=>{
      if(result){
        this.queryList();
      }
    })
  }
}
