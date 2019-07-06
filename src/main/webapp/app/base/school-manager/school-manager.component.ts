import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../service/service-http';
import {NzModalService} from 'ng-zorro-antd/modal';
import {AddSchoolComponent} from './add-school/add-school.component';
import {NzMessageService} from 'ng-zorro-antd/message';
import {EditSchoolComponent} from './edit-school/edit-school.component';
import {RestTimeComponent} from './rest-time/rest-time.component';
@Component({
  selector: 'app-school-manager',
  templateUrl: './school-manager.component.html',
  styleUrls: ['./school-manager.component.css']
})
export class SchoolManagerComponent implements OnInit {

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

  orderByArray = [];

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,) {
  }

  ngOnInit() {
    this.queryList();
  }

  queryList() {
    let params = {};
    if (this.searchName != null) {
      params[this.searchName] = this.searchValue;
    }
    console.debug(params);
    this.loading = true;
    this.http.get('school', params).then((data) => {
      if(data.success){
        this.listOfAllData = data.data;
      }else {
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

  addSchool() {
    let modal = this.modalService.create({
      nzTitle: '新增学校信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddSchoolComponent,
    });
    modal.afterClose.subscribe((d) => {
      if(d){
        this.queryList();
      }
    })
  }

  deleteSchool(id: number) {
    let list = [];
    list.push(id);
    this.modalService.confirm({
      nzTitle: '<i>你真的要删除这个学校信息</i>',
      nzContent: '<b style="color: red;">将会删除该学校里的所有信息</b>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        this.http.delete('school', {ids: list.join()}).then((d) => {
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

  deleteSchools() {
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
        this.http.delete('school', {ids: list.join()}).then((d) => {
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
      nzOnCancel: () => {this.message.info('取消删除');this.buttonLoading = false;}
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

  editSchool(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改学校【' + data.schoolName + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditSchoolComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((d) => {
      if(d){
        this.queryList();
      }
    })
  }

  async openRestTimeManage(data: any) {//学校作息时间管理
    console.debug(data);
    await this.http.get('restTime', {id: data.id}).then((d) => {
      if(d.success){
        this.modalService.create({
          nzTitle: '学校【' + data.schoolName + '】的作息时间表',
          nzFooter: null,
          nzMaskClosable: false,
          nzContent: RestTimeComponent,
          nzComponentParams: d.result[0],
        })
      } else {
        this.message.error(d.result);
      }
      console.debug(d);
    }, e => {
      console.debug(e);
    })
  }

}
