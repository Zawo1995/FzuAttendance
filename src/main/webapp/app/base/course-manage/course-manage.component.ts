import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../service/service-http';
import {NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {AddCourseComponent} from './add-course/add-course.component';
import {EditCourseComponent} from './edit-course/edit-course.component';
import {ArrangeComponent} from './arrange/arrange.component';
import {LocalStorageProvider} from '../../service/local-storage';

@Component({
  selector: 'app-course-manage',
  templateUrl: './course-manage.component.html',
  styleUrls: ['./course-manage.component.css']
})
export class CourseManageComponent implements OnInit {

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
  searchValueSmall;
  searchValueLarge;

  orderByArray = [];

  user;

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService
              ,private local:LocalStorageProvider) {
  }

  ngOnInit() {
    this.user = this.local.get("user");
    this.queryList();
  }

  queryList() {
    let params = {};
    if (this.user.roleId == -2) {//老师的话只查询它创建的班课即可
      params['teacherId'] = this.user.id;
    }
    if (this.searchName != null) {
      if (this.searchName == 'courseTime') {
        params['startWeek'] = typeof this.searchValueSmall === 'undefined' ? null : this.searchValueSmall;
        params['endWeek'] = typeof this.searchValueLarge === 'undefined' ? null : this.searchValueLarge;
      } else if (this.searchName == 'courseOpenTime') {
        params['firstTime'] = new Date(this.searchValue[0]).getTime();
        params['secondTime'] = new Date(this.searchValue[1]).getTime();
      } else {
        params[this.searchName] = this.searchValue;
      }
    }
    console.debug(params);
    this.loading = true;
    this.http.get('course', params).then((data) => {
      this.loading = false;
      if(data.success){
        this.listOfAllData = data.data;
      }else{
        this.message.error(data.message);
      }
      console.debug(data);
    }, (e) => {
      this.message.error('请求服务器出错');
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

  addCourse() {
    let modal = this.modalService.create({
      nzTitle: '新增班课信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddCourseComponent,
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

  deleteCourse(id: number) {
    let list = [];
    list.push(id);
    this.modalService.confirm({
      nzTitle: '<i>你真的要删除这个班课信息</i>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        this.http.delete('course', {ids: list.join()}).then((d) => {
          if (!d.success) {
            this.message.error(d.result)
          } else {
            this.message.success(d.result);
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

  deleteCourses() {
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
        this.http.delete('course', {ids: list.join()}).then((d) => {
          this.buttonLoading = false;
          if (!d.success) {
            this.message.error(d.result)
          } else {
            this.message.success(d.result);
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

  editCourse(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改班课【' + data.courseName + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditCourseComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

  setArange(id: number) {
    //排课
    let data: any = {
      id: id
    }
    let modal = this.modalService.create({
      nzTitle: '排课设置',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: ArrangeComponent,
      nzComponentParams: data,
      nzWidth: '80%'
    });
  }


}
