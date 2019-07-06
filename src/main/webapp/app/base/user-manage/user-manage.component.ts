import {Component, OnInit} from '@angular/core';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalService} from 'ng-zorro-antd/modal';
import {HttpServiceProvider} from '../../service/service-http';
import {AddUserComponent} from './add-user/add-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {AccountManageComponent} from '../account-manage/account-manage.component';
import {AlertService} from '../../service/alert.service';

@Component({
  selector: 'app-user-manage',
  templateUrl: './user-manage.component.html',
  styleUrls: ['./user-manage.component.css']
})
export class UserManageComponent implements OnInit {

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
  searvhValueForSelect;

  orderByArray = [];
  roleSelect = [];

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,
                private alertService:AlertService) {
    this.alertService.messageObserve.subscribe(result => {
      if(result['update']){
        this.queryList();
      }
    })
  }

  ngOnInit() {
    this.initRoleSelect();
    this.queryList();
  }

  initRoleSelect() {
    this.http.get('role', {}).then(d => {
      console.log(d);
      if (d.success) {
        this.roleSelect = d.data;
      } else {
        this.message.error(d.message);
      }
    });
  }

  queryList() {
    let params = {};
    if (this.searchName != null) {
      if (this.searchName == 'registDate') {
        params['registDateBegin'] = new Date(this.searchValue[0]).getTime();
        params['registDateEnd'] = new Date(this.searchValue[1]).getTime();
      } else if (this.searchName == 'roleId') {
        params['roleId'] = this.searvhValueForSelect;
      } else {
        params[this.searchName] = this.searchValue;
      }
    }
    console.debug(params);
    this.loading = true;
    this.http.get('user', params).then((data) => {
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
    this.listOfDisplayData.filter(item => !item.disabled).forEach(item => (this.mapOfCheckedId[item.id] = value));
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

  addUser() {
    let modal = this.modalService.create({
      nzTitle: '新增用户',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddUserComponent,
      nzWidth: '800px'
    });
    modal.afterClose.subscribe((result) => {
      if (result) {
        this.queryList();
      }
    })
  }

  deleteUsers(isBatch: boolean = false, id) {
    let list = [];
    if (!isBatch) {
      list.push(id);
    } else {
      if (this.numberOfChecked == 0) {
        this.message.warning('请至少选择一条信息');
        return;
      }
      this.buttonLoading = true;
      list = this.getCheckedData();
    }
    this.modalService.confirm({
      nzTitle: '<i>删除提示</i>',
      nzContent: '<b style="color: red;">真的删除这些信息吗?</b>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        this.http.delete('user', {ids: list.join()}).then((d) => {
          this.buttonLoading = false;
          if (!d.success) {
            this.message.error(d.message);
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

  editUser(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改用户【' + data.name + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditUserComponent,
      nzComponentParams: data,
      nzWidth: '800px',
    });
    modal.afterClose.subscribe((result) => {
      if (result) {
        this.queryList();
      }
    })
  }

  openAccountSetting(data: any) {
    let modal = this.modalService.create({
      nzTitle: '用户' + data.name + '/账户管理',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AccountManageComponent,
      nzWidth: '70%',
      nzComponentParams: data,
    });
  }
}
