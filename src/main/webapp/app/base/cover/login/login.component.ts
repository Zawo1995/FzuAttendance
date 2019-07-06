import {Component, OnInit} from '@angular/core';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {CanAuthService} from '../../../service/can-auth.service';
import {LocalStorageProvider} from '../../../service/local-storage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading;
  error = '';

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private router: Router, private canAuth: CanAuthService, private local: LocalStorageProvider,
              private activateRoute: ActivatedRoute) {
  }


  ngOnInit() {
    let userName = this.activateRoute.snapshot.queryParams['userName'];
    this.check();
    this.validateForm = this.fb.group({
      userName: [userName, [Validators.required]],
      password: [null, [Validators.required]],
    });
    this.router.navigateByUrl('/cover/login');
  }

  submit() {
    this.error = '';
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      this.buttonLoading = true;
      this.http.post('loginout/login', this.validateForm.value).then(d => {
        this.buttonLoading = false;
        if (d.success) {
          this.message.success(d.message);
          this.local.set('user', d.data);
          this.router.navigateByUrl("/menus");
        } else {
          this.error = d.message;
        }
        console.log(d);
      })
    }
  }

  check() {
    let user = this.local.get('user', null);
    if (user != null) {
      this.http.get('loginout/check', {code: user.id}).then(d => {
        console.log(d);
        //重定向 直接登录
        if (d.success) {
          this.router.navigateByUrl('/menus');
          this.message.success('登录成功');
        } else {
          console.debug('自动登录失败')
        }
      })
    }
  }



}
