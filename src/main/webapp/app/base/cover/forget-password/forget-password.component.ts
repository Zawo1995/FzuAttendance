import {Component, OnInit} from '@angular/core';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LocalStorageProvider} from '../../../service/local-storage';
import {Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzNotificationService} from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.less']
})
export class ForgetPasswordComponent implements OnInit {

  current = 0;
  form1: FormGroup;
  form2: FormGroup;
  buttonLoading = false;
  count;//验证码倒计时
  interval$: any;
  status = 'pool';
  visible = false;
  progress = 0;
  passwordProgressMap = {
    ok: 'success',
    pass: 'normal',
    pool: 'exception',
  };
  userId;//验证码获得后从服务器返回的userId

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private router: Router, private local: LocalStorageProvider, private notification: NzNotificationService) {
  }

  ngOnInit() {
    this.form1 = this.fb.group({
      mobile: [null, [Validators.required, Validators.pattern(/^1[3456789]\d{9}$/)]],
      captcha: [null, [Validators.required]],
    })
    this.form2 = this.fb.group({
      password: [null, [Validators.required, Validators.minLength(6), ForgetPasswordComponent.checkPassword.bind(this)]],
      confirm: [null, [Validators.required, this.checkConfirm]],
    })
  }

  submitOne() {
    console.debug(this.form1);
    for (const i in this.form1.controls) {
      this.form1.controls[i].markAsDirty();
      this.form1.controls[i].updateValueAndValidity();
    }
    console.debug(this.form1.value);
    if (this.form1.valid) {
      this.buttonLoading = true;
      this.http.post('forgetPwd/check', this.form1.value).then(d => {
        this.buttonLoading = false;
        if (d.success) {
          this.current++;
        } else {
          this.createErrorNotification(d.message);
        }
        console.log(d);
      })
    }
  }


  static checkPassword(control: FormControl) {
    if (!control) return null;
    const self: any = this;
    self.visible = !!control.value;
    if (control.value && control.value.length > 9) {
      self.status = 'ok';
    } else if (control.value && control.value.length > 5) {
      self.status = 'pass';
    } else {
      self.status = 'pool';
    }
    if (self.visible) {
      self.progress = control.value.length * 10 > 100 ? 100 : control.value.length * 10;
    }
  }

  checkConfirm = (control: FormControl) => {//用于验证二次密码
    if (!control.value) {
      return {required: true};
    } else if (control.value !== this.form2.value.password) {
      return {confirm: true};
    }
  }

  submitTwo() {
    console.debug(this.form2);
    for (const i in this.form2.controls) {
      this.form2.controls[i].markAsDirty();
      this.form2.controls[i].updateValueAndValidity();
    }
    console.debug(this.form2.value);
    if (this.form2.valid) {
      this.buttonLoading = true;
      this.form2.value['id'] = this.userId;
      this.http.post('forgetPwd/updatePwd', this.form2.value).then(d => {
        this.buttonLoading = false;
        if (d.success) {
          this.current++;
        } else {
          this.createErrorNotification(d.message);
        }
        console.log(d);
      })
    }
  }

  get mobile() {
    return this.form1.controls['mobile'];
  }

  //发送验证码
  getCaptcha() {
    if (this.mobile.invalid) {
      this.mobile.markAsDirty({onlySelf: true});
      this.mobile.updateValueAndValidity({onlySelf: true});
      return;
    }
    this.http.post('forgetPwd/sendCaptchaMessage', {mobile: this.form1.value.mobile}).then(d => {
      if (d.success) {
        this.userId = d.data;
        this.message.success(d.message);
        this.count = 59;
        this.interval$ = setInterval(() => {
          this.count -= 1;
          if (this.count <= 0) clearInterval(this.interval$);
        }, 1000);
      } else {
        this.createErrorNotification(d.message);
      }
    })

  }

  createErrorNotification(text: string) {
    this.notification.create(
      'error',
      text,
      '',
    );
  }

  pre() {
    this.current--;
  }
}
