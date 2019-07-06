import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {UploadFile} from 'ng-zorro-antd/upload';
import {AlertService} from '../../../service/alert.service';
import {LocalStorageProvider} from '../../../service/local-storage';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  private validateForm: FormGroup;
  buttonLoading = false;
  roleOption = [];
  orgOption = [];
  isMore = false;
  imageUrl;

  user ;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef, private alertService: AlertService,private local:LocalStorageProvider) {
    this.user = this.local.get("user");
  }

  collegeId;
  schoolId;
  async ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.imageUrl = this.modalRef.getContentComponent().imageUrl;
    this.collegeId = this.modalRef.getContentComponent().collegeId;
    this.schoolId = this.modalRef.getContentComponent().schoolId;
    let orgValues = [this.schoolId, this.collegeId];
    this.validateForm = this.fb.group({
      id: [this.modalRef.getContentComponent().id, []],
      phone: [this.modalRef.getContentComponent().phone, [Validators.required, Validators.pattern(/^1[3456789]\d{9}$/)]],
      passwordReadonly: ['******************', []],
      name: [this.modalRef.getContentComponent().name, [Validators.required]],
      roleId: [this.modalRef.getContentComponent().roleId, [Validators.required]],
      orgValues: [orgValues, [Validators.required]],
    });
    this.initRoleOption();
    await this.initOrganizationCascader();
  }

  submitForm() {
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      this.validateForm.value['collegeId'] = this.validateForm.value.orgValues[1];
      this.buttonLoading = true;
      this.http.put('user', this.validateForm.value).then(async d => {
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
                if (c.id == this.collegeId) {
                  this.validateForm.patchValue({orgValues:[s.id, c.id]});
                  console.log(this.validateForm.value.orgValues);
                }
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

  togglePasswordSetting() {
    this.isMore = !this.isMore;
    if (this.isMore) {
      this.validateForm.addControl('password', new FormControl(null, [Validators.required]));
      this.validateForm.addControl('checkPassword', new FormControl(null, [Validators.required, this.confirmationValidator]));
    } else {
      this.validateForm.removeControl('password');
      this.validateForm.removeControl('checkPassword');
    }
  }

  uploadLoading = false;

  handleChange(info: { file: UploadFile }) {
    switch (info.file.status) {
      case 'uploading':
        this.uploadLoading = true;
        break;
      case 'done':
        this.uploadLoading = false;
        if (info.file.response.success) {
          this.imageUrl = info.file.response.data
          this.message.success(info.file.response.message);
          this.alertService.success({
            userUpdate: true
          });//发布一个消息
        } else {
          this.message.error(info.file.response.message);
        }
        break;
      case 'error':
        this.message.error('网络异常');
        this.uploadLoading = false;
        break;
    }
  }


}
