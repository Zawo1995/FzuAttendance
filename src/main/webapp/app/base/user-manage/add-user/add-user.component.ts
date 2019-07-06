import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;
  roleOption = [];
  orgOption = [];
  isMore = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  async ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      phone: [null, [Validators.required, Validators.pattern(/^1[3456789]\d{9}$/)]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      name: [null, [Validators.required]],
      roleId: [null, [Validators.required]],
      orgValues: [null, [Validators.required]],
    });
    this.initRoleOption();
    await this.initOrganizationCascader();
    console.log(this.orgOption, 'orgOption');
  }

  submitForm() {
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      let accountList = [{
        method:'mobile',
        loginAccount:this.validateForm.value.phone,
      }];
      if(this.validateForm.value.emailChecked){
        accountList.push({
          method:'mail',
          loginAccount:this.validateForm.value.email,
        })
      }
      if(this.validateForm.value.qqChecked){
        accountList.push({
          method:'qq',
          loginAccount:this.validateForm.value.qq,
        })
      }
      if(this.validateForm.value.weChatChecked){
        accountList.push({
          method:'wechat',
          loginAccount:this.validateForm.value.weChat,
        })
      }
      console.log(accountList);
      this.validateForm.value['collegeId'] = this.validateForm.value.orgValues[1];
      this.validateForm.value['registDate'] = new Date().getTime();
      this.validateForm.value['AccountAddListStr'] = JSON.stringify(accountList);
      this.buttonLoading = true;
      this.http.post('user', this.validateForm.value).then(async d => {
        if (d.success) {
          this.message.success(d.message);
          this.modalRef.close(true);
        } else {
          this.message.error(d.message);
        }
        this.buttonLoading = false;
      }, e => {
        this.buttonLoading = false;
        this.message.error('服务器异常');
        console.debug(e);
      })
    }
  }


  confirmationValidator = (control: FormControl) => {//用于验证二次密码
    if (!control.value) {
      return {required: true};
    } else if (control.value !== this.validateForm.value.password) {
      return {confirm: true};
    }
  }

  updateConfirmValidator(): void {
    Promise.resolve().then(() => this.validateForm.controls['checkPassword'].updateValueAndValidity());
  }

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
  }

  initRoleOption() {
    this.http.get('role', {}).then((d) => {
      if (d.success) {
        this.roleOption = d.data
      } else {
        this.message.error(d.message);
      }
    }, e => {
      this.message.error("服务器异常");
    })
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

  toggleMore() {
    this.isMore = !this.isMore;
    if (this.isMore) {
      this.validateForm.addControl('email', new FormControl(null, [Validators.email]));
      this.validateForm.addControl('emailChecked', new FormControl(false));
      this.validateForm.addControl('qq', new FormControl(null, Validators.pattern(/^[1-9][0-9]{4,9}$/)));
      this.validateForm.addControl('qqChecked', new FormControl(false));
      this.validateForm.addControl('weChat', new FormControl(null, Validators.pattern(/^[a-zA-Z0-9_]+$/)));
      this.validateForm.addControl('weChatChecked', new FormControl(false));
    } else {
      this.validateForm.removeControl('email');
      this.validateForm.removeControl('emailChecked');
      this.validateForm.removeControl('qq');
      this.validateForm.removeControl('qqChecked');
      this.validateForm.removeControl('weChat');
      this.validateForm.removeControl('weChatChecked');
    }
  }
}
