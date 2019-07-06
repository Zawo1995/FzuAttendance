import {Component, OnInit, ViewChild} from '@angular/core';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzTreeComponent} from 'ng-zorro-antd/tree';
import {log} from 'util';

@Component({
  selector: 'app-power-setting',
  templateUrl: './power-setting.component.html',
  styleUrls: ['./power-setting.component.css']
})
export class PowerSettingComponent implements OnInit {
  @ViewChild('nzTreeComponent', {}) nzTreeComponent: NzTreeComponent;
  menuTree = [];
  loading = false;
  roleId;
  checkedMenus = [];//初始化checkbox状态
  targetMenuData = [];

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService,
              private modalRef: NzModalRef,) {
  }

  ngOnInit() {
    this.roleId = this.modalRef.getContentComponent().id;
    this.queryList();
    this.queryTargetMenuId();
  }

  async queryTargetMenuId() {
    let checked = [];
    this.loading = true;
    await this.http.get('rolemenu', {roleId: this.roleId}).then(d => {
      console.log(d);
      if (!d.success) {
        this.message.error(d.message);
      } else {
        this.targetMenuData = d.data;
        for (let i of d.data) {
          if(i.enable == 1){
            checked.push(i.menuId);
          }
        }
        this.checkedMenus = checked;

      }
    })
  }

  async queryList() {
    this.loading = true;
    await this.http.get('menu', {}).then(d => {
      this.loading = false;
      if (d.success) {
        this.mergeToTree(d.data);
      } else {
        this.message.error(d.message);
      }
    })
  }

  mergeToTree(data) {
    let treeData = [];
    let parentIndex = 1;
    for (let i of data) {
      if (i.parentId == 0) {
        treeData.push({
          key: i.id,
          title: i.name,
          data: i,
          index: parentIndex++,
          children: [],
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
              index: t.children.length + 1,
              children: [],
              isLeaf: true,
            });
          }
        }
      }
    }
    this.menuTree = treeData;
  }

  save() {
    this.loading = true;
    console.log(this.nzTreeComponent.getCheckedNodeList());//还要获取到所有二级节点
    for (let i of this.targetMenuData) {
      i.enable = 0;//重置为0
    }
    for (let node of this.nzTreeComponent.getCheckedNodeList()) {
      if (node.origin.children.length > 0) {
        for (let c of node.origin.children) {
          this.targetMenuData.filter(item => item.menuId == c.key)[0].enable = 1;
        }
        this.targetMenuData.filter(item => item.menuId == node.key)[0].enable = 1;
      } else {
        this.targetMenuData.filter(item => item.menuId == node.key)[0].enable = 1;//命中则将enable置1
      }
    }
    console.log(this.targetMenuData);
    this.http.put('rolemenu', {roleMenuListStr: JSON.stringify(this.targetMenuData)}).then(d=>{
      this.loading = false;
      if(d.success){
        this.message.success(d.message);
        this.modalRef.close(true);
      }else {
        this.message.error(d.message);
      }
    })
  }

}
