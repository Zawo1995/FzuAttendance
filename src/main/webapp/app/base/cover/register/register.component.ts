import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {Router} from '@angular/router';
import {LocalStorageProvider} from '../../../service/local-storage';
import {CanAuthService} from '../../../service/can-auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading;
  error = '';

  status = 'pool';
  visible = false;
  progress = 0;
  passwordProgressMap = {
    ok: 'success',
    pass: 'normal',
    pool: 'exception',
  };
  count;//验证码倒计时
  interval$: any;
  roleOption = [];
  orgOption = [];

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private router: Router, private canAuth: CanAuthService, private local: LocalStorageProvider) {
  }

  async ngOnInit() {
    this.validateForm = this.fb.group({
      mail: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6), RegisterComponent.checkPassword.bind(this)]],
      confirm: [null, [Validators.required, this.checkConfirm]],
      mobile: [null, [Validators.required, Validators.pattern(/^1[3456789]\d{9}$/)]],
      captcha: [null, [Validators.required]],
      roleId: [null, [Validators.required]],
      orgValues: [null, [Validators.required]],
    });
    this.initRoleOption();
    await this.initOrganizationCascader();
  }

  async initOrganizationCascader() {
    //先查出所有学校
    let orgs = [];
    await this.http.get('school', {}).then(async d => {
      if (d.success) {
        for (let s of d.data) {
          let value = s.id;
          let label = s.schoolName;
          //一一查询每一个子学院
          await this.http.get('college', {schoolId: s.id}).then(d => {
            if (d.success) {
              let children = [];
              for (let c of d.data) {
                children.push({
                  value: c.id,
                  label: c.collegeName,
                  isLeaf: true,
                })
              }
              orgs.push({
                value: value,
                label: label,
                children: children
              });
            } else {
              this.message.error(d.message);
            }
          })
        }
        console.log(orgs, 'orgs');
        this.orgOption = orgs;
      } else {
        this.message.error(d.message);
      }
    })
  }

  initRoleOption() {
    this.roleOption = [{name: '教师', id: -2}, {name: '学生', id: -3}]
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
    } else if (control.value !== this.validateForm.value.password) {
      return {confirm: true};
    }
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
      this.validateForm.value['collegeId'] = this.validateForm.value.orgValues[1];
      this.http.post('register/register', this.validateForm.value).then(d => {
        this.buttonLoading = false;
        if (d.success) {
          this.message.success(d.message);
          this.router.navigateByUrl('/menus');
        } else {
          this.error = d.message;

        }
        console.log(d);//
      })
    }
  }


  get mobile() {
    return this.validateForm.controls['mobile'];
  }

  //发送验证码
  getCaptcha() {
    if (this.mobile.invalid) {
      this.mobile.markAsDirty({onlySelf: true});
      this.mobile.updateValueAndValidity({onlySelf: true});
      return;
    }
    this.http.post('register/sendCaptchaMessage', {telphone: this.validateForm.value.mobile}).then(d => {
      if (d.success) {
        this.message.success(d.message);
        this.count = 59;
        this.interval$ = setInterval(() => {
          this.count -= 1;
          if (this.count <= 0) clearInterval(this.interval$);
        }, 1000);
      } else {
        this.error = d.message;
      }
    })

  }

}
