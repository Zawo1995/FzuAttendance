import {Component, OnInit} from "@angular/core";
import {HttpServiceProvider} from "../app/service/service-http";
import {LocalStorageProvider} from '../app/service/local-storage';
import {NzMessageService} from 'ng-zorro-antd/message';
import {Router} from '@angular/router';
import {NzModalService} from 'ng-zorro-antd/modal';
import {EditUserComponent} from '../app/base/user-manage/edit-user/edit-user.component';
import {AlertService} from '../app/service/alert.service';
import {AccountManageComponent} from '../app/base/account-manage/account-manage.component';
@Component({selector: 'app-menus', templateUrl: './menus.html', styleUrls: ['./page-menage.css']})
export class PageMenusComponent implements OnInit {
  isCollapsed = false;
  isReverseArrow = false;
  menuTree = [];
  user;

  constructor(private local: LocalStorageProvider, private http: HttpServiceProvider, private message: NzMessageService,
              private router: Router, private modalService: NzModalService, private alertService: AlertService) {
    alertService.messageObserve.subscribe((res) => {
      if (res['update']) {
        this.user = local.get("user");
      }
      if(res['menuUpdate']){
        console.log(res);
        this.http.get('menu', {}).then(d => {
          this.mergeToTree(d.data);
        })
      }
    })
  }

  logout() {
    this.local.remove('user');
    this.http.post("loginout/logout").then(d => {
      if (d.success) {
        this.message.success(d.message);
        console.log('登出');
        this.router.navigateByUrl('/cover/login');
      } else {
        this.message.error(d.message);
      }
    })
  }

  lock() {
    this.router.navigateByUrl("/cover/lock");
  }

  editUser() {
    let modal = this.modalService.create({
      nzTitle: '修改个人信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditUserComponent,
      nzComponentParams: this.user,
      nzWidth: '800px'
    });
    modal.afterClose.subscribe((result) => {
      if (result) {
        this.http.get("user", {id: this.user.id}).then(d => {
          if (d.success) {
            this.user = d.data[0];
            this.local.set("user", this.user);
          } else {
            this.message.error(d.message);
          }
        })
      }
    })
  }

  editAccount() {
    let modal = this.modalService.create({
      nzTitle: '账户设置',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: AccountManageComponent,
      nzWidth: '70%',
      nzComponentParams: this.user,
    });
  }

  ngOnInit() {
    this.user = this.local.get("user");
    this.http.get('menu', {roleId:this.user.roleId}).then(d => {
      this.mergeToTree(d.data);
    })

  }

  mergeToTree(data) {
    let treeData = [{
      key: -1,
      title: '首页',
      data: {
        url: '/menus',
        icon: 'dashboard',
        name: '首页',
      },
      children: []
    }];
    for (let i of data) {
      if (i.parentId == 0) {
        treeData.push({
          key: i.id,
          title: i.name,
          data: i,
          children: []
        });
      }
    }
    for (let i of data) {
      if (i.parentId != 0) {
        for (let t of treeData) {
          if (i.parentId == t.key) {
            t.children.push({
              key: i.id,
              title: i.name,
              data: i,
              children: []
            });
          }
        }
      }
    }
    this.menuTree = treeData;
  }
}
@Component({selector: 'app-footer', templateUrl: './page-footer.html', styleUrls: ['./page-menage.css']})
export class FooterComponent implements OnInit {
  constructor() {
  }

  ngOnInit() {
  }
}
@Component({selector: 'app-menulist', templateUrl: './menulist.html', styleUrls: ['./page-menage.css']})
export class MeauListComponent implements OnInit {


  constructor(private http: HttpServiceProvider,) {
  }

  ngOnInit() {
  }


}
