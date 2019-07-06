import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpServiceProvider} from '../../service/service-http';
import {NzTreeComponent} from 'ng-zorro-antd/tree';
import {NzTreeNode} from 'ng-zorro-antd/core/tree/nz-tree-base-node';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalService} from 'ng-zorro-antd/modal';
import {EditMenuComponent} from './edit-menu/edit-menu.component';
import {AlertService} from '../../service/alert.service';

@Component({
  selector: 'app-menu-manage',
  templateUrl: './menu-manage.component.html',
  styleUrls: ['./menu-manage.component.css']
})
export class MenuManageComponent implements OnInit {
  @ViewChild('nzTreeComponent', {}) nzTreeComponent: NzTreeComponent;

  menuTree = [];
  totalNum = 0;
  totalParentNum = 0;
  enableUp = false;
  enableDown = false;
  enableEdit = false;
  loading = false;

  constructor(private http: HttpServiceProvider, private message: NzMessageService, private modalService: NzModalService,
            private alertService:AlertService) {
  }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.enableUp = false;
    this.enableDown = false;
    this.menuTree = [];
    this.loading = true;
    this.http.get('menu', {}).then(d => {
      this.loading = false;
      if (d.success) {
        this.alertService.success({menuUpdate:true});
        this.totalNum = d.data.length;
        this.mergeToTree(d.data);
        this.totalParentNum = this.menuTree.length;
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

  updateButtonState(event) {
    this.enableEdit = this.nzTreeComponent.getSelectedNodeList().length > 0 ? true : false;
    if (event.node.level == 0) {//如果是父节点
      this.enableUp = event.node.origin.index != 1 ? true : false;
      this.enableDown = event.node.origin.index != this.totalParentNum ? true : false;
    }
    else if (event.node.level == 1) {//子节点
      let number = event.node.parentNode.children.length;//获取同辈个数
      this.enableUp = event.node.origin.index > 1 ? true : false;
      this.enableDown = event.node.origin.index != number ? true : false;
    }
  }

  up() {//交换上一个节点和当前节点
    if (this.nzTreeComponent.getSelectedNodeList().length == 0) {
      this.message.warning("请选择菜单");
      return;
    }
    let nowNode: NzTreeNode = this.nzTreeComponent.getSelectedNodeList()[0];
    let targetNode;
    if (nowNode.level == 0) {//父节点的目标是比他inedx少1的node
      for (let node of this.nzTreeComponent.getTreeNodes()) {
        if (node.origin.index == nowNode.origin.index - 1) {
          targetNode = node;
          break;
        }
      }
    } else {
      for (let node of nowNode.getParentNode().children) {
        if (node.origin.index == nowNode.origin.index - 1) {
          targetNode = node;
          break;
        }
      }
    }
    this.exChangeNode(nowNode, targetNode);
  }

  down() {//交换下一个节点和当前节点
    if (this.nzTreeComponent.getSelectedNodeList().length == 0) {
      this.message.warning("请选择菜单");
      return;
    }
    let nowNode: NzTreeNode = this.nzTreeComponent.getSelectedNodeList()[0];
    let targetNode;
    if (nowNode.level == 0) {//父节点的目标是比他inedx少1的node
      for (let node of this.nzTreeComponent.getTreeNodes()) {
        if (node.origin.index == nowNode.origin.index + 1) {
          targetNode = node;
          break;
        }
      }
    } else {
      for (let node of nowNode.getParentNode().children) {
        if (node.origin.index == nowNode.origin.index + 1) {
          targetNode = node;
          break;
        }
      }
    }
    this.exChangeNode(nowNode, targetNode);
  }

  //交换两个节点
  exChangeNode(node, targetNode) {
    //改数据库之后刷新就可以了
    let menu = node.origin.data;
    menu['targetId'] = targetNode.origin.data.id;//要更换的目标id
    menu['targetOrder'] = targetNode.origin.data.order;//要更换的目标id
    this.loading = true;
    this.http.put('menu', menu).then(d => {
      this.loading = false;
      if (d.success) {
        this.refresh();
        this.message.success(d.message);
      } else {
        this.message.error(d.message);
      }
    })
  }

  edit() {
    if (this.nzTreeComponent.getSelectedNodeList().length == 0) {
      this.message.warning("请选择菜单");
      return;
    }
    let data = this.nzTreeComponent.getSelectedNodeList()[0].origin.data;
    let modal = this.modalService.create({
      nzTitle: '修改菜单【' + data.name + '】的信息',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: EditMenuComponent,
      nzComponentParams: data,
    });
    modal.afterClose.subscribe((d) => {
      if (d) {
        this.refresh();
      }
    })
  }


}
