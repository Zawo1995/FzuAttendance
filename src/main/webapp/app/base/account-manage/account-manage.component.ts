import {Component, OnInit} from '@angular/core';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {HttpServiceProvider} from '../../service/service-http';
import {AddAccountComponent} from './add-account/add-account.component';

@Component({
  selector: 'app-account-manage',
  templateUrl: './account-manage.component.html',
  styleUrls: ['./account-manage.component.css']
})
export class AccountManageComponent implements OnInit {

  loading = false;
  datas = [];
  mapForMethod: { [key: string]: {} } = {
    'mobile': {method: '手机', icon: 'mobile',},
    'mail': {method: '邮箱', icon: 'mail',},
    'qq': {method: 'qq', icon: 'qq',},
    'wechat': {method: '微信', icon: 'wechat',},
  }

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.log(this.modalRef.getContentComponent());
    this.queryList();
  }

  queryList() {
    this.loading = true;
    console.log(this.modalRef.getContentComponent().id);
    this.http.get('account', {userId: this.modalRef.getContentComponent().id}).then(d => {
      this.loading = false;
      if (d.success) {
        this.datas = d.data;
      } else {
        this.message.error(d.message);
      }
      console.debug(d);
    }, e => {
      this.message.error('请求服务器错误');
      console.debug(e);
    })
  }

  addAccount() {
    let data:any = {
      userId:this.modalRef.getContentComponent().id,
    }
    let modal = this.modalService.create({
      nzTitle: '新增登录账户信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzComponentParams: data,
      nzContent: AddAccountComponent,
    });
    modal.afterClose.subscribe((d) => {
      if(d){
        this.queryList();
      }
    })
  }

  deleteAccount(data) {
    this.http.delete('account', {id: data.id}).then(d => {
      if (d.success) {
        this.message.success(d.message);
        this.queryList();
      } else {
        this.message.error(d.message);
      }
    }, e => {
      console.debug(e);
      this.message.success('服务器异常');
    })
  }

  editAccount(data) {
    console.log(data);
    let modal = this.modalService.create({
      nzTitle: '修改登录账户信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzComponentParams: data,
      nzContent: AddAccountComponent,
    });
    modal.afterClose.subscribe((d) => {
      if(d){
        this.queryList();
      }
    })
  }

}
