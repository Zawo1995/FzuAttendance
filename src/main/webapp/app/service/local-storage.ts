import {Injectable} from '@angular/core';
import {AlertService} from './alert.service';
import {HttpServiceProvider} from './service-http';
import {NzMessageService} from 'ng-zorro-antd/message';

/*
 Generated class for the LocalStorageProvider provider.

 See https://angular.io/guide/dependency-injection for more info on providers
 and Angular DI.
 */
@Injectable()
export class LocalStorageProvider {

  private storage = window.localStorage;

  constructor(private alertService: AlertService, private http: HttpServiceProvider, private message: NzMessageService,) {
    alertService.messageObserve.subscribe(res => {//在本地存储中订阅用户的改变，直接修改本地存储的user信息
      if (res['userUpdate']) {
        let userOld = this.get('user', null);
        let user;
        this.http.get('user', {id: userOld.id}).then(d => {
          if (d.success) {
            user = d.data[0];
            this.set('user', user);
          } else {
            this.message.error(d.message);
          }
        });

      }
    })
  }

  get(key: string, defaultValue?: any): any {
    let value: any = this.storage.getItem(key);
    try {
      value = JSON.parse(value);
    } catch (error) {
      value = null;
    }
    if (value === null && defaultValue) {
      value = defaultValue;
    }
    return value;
  }

  set(key: string, value: any) {
    this.storage.setItem(key, JSON.stringify(value));
  }

  remove(key: string) {
    this.storage.removeItem(key);
  }

}
