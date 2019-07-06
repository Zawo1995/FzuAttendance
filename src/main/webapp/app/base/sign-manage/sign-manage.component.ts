import {Component, OnInit} from '@angular/core';
import {LocalStorageProvider} from '../../service/local-storage';
import {NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../service/service-http';
import {SignDetailComponent} from './sign-detail/sign-detail.component';

@Component({
  selector: 'app-sign-manage',
  templateUrl: './sign-manage.component.html',
  styleUrls: ['./sign-manage.component.less']
})
export class SignManageComponent implements OnInit {

  listOfDisplayData = [];
  listOfAllData = [];
  isAllDisplayDataChecked = false;
  mapOfCheckedId: { [key: number]: boolean } = {};
  numberOfChecked = 0;
  isIndeterminate = false;
  loading = true;
  buttonLoading = false;

  searchName: string;
  searchValue: string;
  searchDayNum: number;
  orderByArray = [];
  user;


  constructor(private http: HttpServiceProvider, private message: NzMessageService, private modalService: NzModalService,
              private local: LocalStorageProvider) {
    this.user = local.get("user",);
  }

  ngOnInit() {
    this.queryList();
  }


  queryList() {
    let params = {};
    if (this.user.roleId == -2) {//老师的话只查询它创建的班课即可
      params['teacherId'] = this.user.id;
    }
    if (this.searchName != null) {
      if (this.searchName == 'startTime') {
        params['startTimeBegin'] = new Date(this.searchValue[0]).getTime();
        params['startTimeEnd'] = new Date(this.searchValue[1]).getTime();
      } else if (this.searchName == 'dayNum') {
        params['dayNum'] = this.searchDayNum;
      } else {
        params[this.searchName] = this.searchValue;
      }
    }
    console.debug(params);
    this.loading = true;
    this.http.get("sign/signTask", params).then(data => {
      this.loading = false;
      console.debug(data);
      if (data.success) {
        this.listOfAllData = data.data;
      } else {
        this.message.error(data.message);
      }
    }, e => {
      console.log(e);
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

  openSignDetail(data) {
    let param: any = {signId: data.signId, courseId: data.id};
    let modal = this.modalService.create({
      nzTitle: '签到详情',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: SignDetailComponent,
      nzComponentParams: param,
      nzWidth: '70%'
    });
  }

}
