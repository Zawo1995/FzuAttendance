import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../service/service-http';
import {NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {AddCollegeComponent} from './add-college/add-college.component';
import {EditCollegeComponent} from './edit-college/edit-college.component';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {Router} from '@angular/router';

@Component({
  selector: 'app-college-manage',
  templateUrl: './college-manage.component.html',
  styleUrls: ['./college-manage.component.css']
})
export class CollegeManageComponent implements OnInit {

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
  schoolOption = [];
  selectedSchoolId;
  orderByArray = [];


  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,
              private router: Router, private notification: NzNotificationService) {
  }

  ngOnInit() {
    this.initSchoolOption();
  }

  initSchoolOption() {
    this.http.get('school').then((data) => {
      this.loading = false;
      if (data.success) {
        this.schoolOption = data.data;
        if (this.schoolOption.length == 0) {
          this.router.navigateByUrl("/menus/course-manage");
          this.notification.create(
            'info',
            '操作非法',
            '您还没有任何学校，请先去添加学校'
          );
        }else {
          this.selectedSchoolId = data.data[0].id;
          this.queryList();
        }
      }else {
        this.message.error(data.message);
      }
      console.debug(data);
    }, (e) => {
      console.debug(e);
    })
  }

  queryList() {
    let params = {
      schoolId: this.selectedSchoolId,
    };
    if (this.searchName != null) {
      params[this.searchName] = this.searchValue;
    }
    console.debug(params);
    this.loading = true;
    this.http.get('college', params).then((data) => {
      if(data.success){
        this.listOfAllData = data.data;
      }else{
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

  addCollege() {
    let school = this.getSelectSchoolById(this.selectedSchoolId);
    let modal = this.modalService.create({
      nzTitle: '新增 ' + '<i style="font-size: large"> ' + school.schoolName + '</i>' + ' 的学院',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddCollegeComponent,
      nzComponentParams: school
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

  editCollege(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改学院【' + data.collegeName + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditCollegeComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

  deleteOneCollege(id: number) {
    let list = [];
    list.push(id);
    this.modalService.confirm({
      nzTitle: '<i>你真的要删除这个学院信息</i>',
      nzOkText: '确定',
      nzOkType: 'danger',
      nzOnOk: () => {
        this.http.delete('college', {ids: list.join()}).then((d) => {
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

  deleteColleges() {
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
        this.http.delete('college', {ids: list.join()}).then((d) => {
          this.buttonLoading = false;
          if (!d.success) {
            this.message.error(d.result);
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
      nzOnCancel: () => {
        this.message.info('取消删除');
        this.buttonLoading = false;
      }
    })
  }

  getSelectSchoolById(id: number) {
    for (let i of this.schoolOption) {
      if (i.id == id) {
        return i;
      }
    }
  }

}
