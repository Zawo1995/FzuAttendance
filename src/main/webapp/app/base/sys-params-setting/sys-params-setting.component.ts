import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../service/service-http';
import {NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';

@Component({
  selector: 'app-sys-params-setting',
  templateUrl: './sys-params-setting.component.html',
  styleUrls: ['./sys-params-setting.component.css']
})
export class SysParamsSettingComponent implements OnInit {

  loading = false;
  datas = [];

  constructor(private http: HttpServiceProvider, private modalService: NzModalService, private message: NzMessageService) {
  }

  ngOnInit() {
    this.queryList();
  }

  queryList() {
    this.loading = true;
    this.http.get('systemParam').then(d => {
      this.loading = false;
      if(d.success){
        for (let i of d.result) {
          i['edit'] = false;
        }
        this.datas = d.result;
      } else {
        this.message.error(d.result);
      }
      console.debug(d);
    }, e => {
      console.debug(e);
    })
  }

  editParam(data) {
    data.edit = true;
  }

  reset(data) {
    data.value = data.defaultValue;
    this.http.put('systemParam', data).then(d => {
      if(d.success){
        this.message.success("重置成功");
      }else {
        this.message.error('重置失败');
      }
    }, e => {
      console.debug(e);
    })
  }

  saveParam(data) {
    this.http.put('systemParam', data).then(d => {
      if (d.success) {
        this.message.success(d.result);
        data.edit = false;
      } else {
        this.message.error(d.result);
      }
    }, e => {
      console.debug(e);
      this.message.success('服务器异常');
    });

  }

  cancel(data) {//取消保存 还原数据
    this.http.get('systemParam', {id: data.id}).then(d => {
      if (d != null) {
        console.debug(d);
        data.edit = false;
        data.value = d[0].value;//还原数据
      }
    }, e => {
      console.debug(e);
    })
  }

}
