import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/index';
import {HttpServiceProvider} from './service-http';
import {LocalStorageProvider} from './local-storage';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzNotificationService} from 'ng-zorro-antd/notification';

@Injectable({
  providedIn: 'root'
})
export class CanAuthService implements CanActivate {

  constructor(private http: HttpServiceProvider, private local: LocalStorageProvider, private router: Router,
              private message: NzMessageService, private notification: NzNotificationService) {
  }

  check(): Observable<boolean> {
    return new Observable((ob) => {
      console.log("检测登录");
      let user = this.local.get('user', null);
      if (user == null) {
        // this.message.error('用户已过期，请重新登录');
        this.createErrorNotification();
        this.router.navigateByUrl('/cover/login');
        ob.next(false);
        ob.complete();
      } else {
        this.http.get('loginout/check', {code: user.id}).then(d => {
          if (d.success) {
            ob.next(true);
          } else {
            this.createErrorNotification();
            this.router.navigateByUrl('/cover/login');
            ob.next(false);
          }
          ob.complete();
        })
      }
    })

  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> | boolean {
    return this.check();
  }

  createErrorNotification() {
    this.notification.create(
      'error',
      '请重新登录',
      '未登录或登录用户已过期'
    );
  }


}
