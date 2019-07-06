import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {AddArrangeComponent} from './add-arrange/add-arrange.component';
import {EditArrangeComponent} from './edit-arrange/edit-arrange.component';

@Component({
  selector: 'app-arrange',
  templateUrl: './arrange.component.html',
  styleUrls: ['./arrange.component.css']
})
export class ArrangeComponent implements OnInit {
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

  constructor(private http: HttpServiceProvider, private message: NzMessageService, private modalService: NzModalService,
              private modalRef: NzModalRef) {
    console.log(modalRef);
  }

  ngOnInit() {
    console.log(this.modalRef.getContentComponent());
    this.queryList();
  }

  queryList() {
    let params = {courseId: this.modalRef.getContentComponent().id};
    if (this.searchName != null) {
      if (this.searchName == 'lessonBegin' || this.searchName == 'lessonLength') {
        params[this.searchName] = this.searchValueSmall + ':' + this.searchValueLarge
      } else {
        params[this.searchName] = this.searchValue;
      }
    }
    console.debug(params);
    this.loading = true;
    this.http.get("arrange", params).then(data => {
      this.loading = false;
      console.debug(data);
      if(data.success){
        this.listOfAllData = data.result;
      }else{
        this.message.error(data.result);
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

  addArrange() {
    let data:any = {
      courseId: this.modalRef.getContentComponent().id
    }
    let modal = this.modalService.create({
      nzTitle: '新增排课信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AddArrangeComponent,
      nzComponentParams:data,
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

  deleteArrange(id: number) {
    let list = [];
    list.push(id);
    this.http.delete('arrange', {ids: list.join()}).then((d) => {
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
  }

  deleteArranges() {
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
        this.http.delete('arrange', {ids: list.join()}).then((d) => {
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

  editArrange(data: any) {
    let modal = this.modalService.create({
      nzTitle: '修改排课信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditArrangeComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((result) => {
      if(result){
        this.queryList();
      }
    })
  }

}
