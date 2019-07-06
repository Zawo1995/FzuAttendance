import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;
  userId: number;
  isEdit: boolean = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    if (this.modalRef.getContentComponent().id != null) {//编辑窗口
      this.isEdit = true;
      this.validateForm = this.fb.group({
        id: [this.modalRef.getContentComponent().id],
        method: [this.modalRef.getContentComponent().method, [Validators.required]],
        loginAccount: [this.modalRef.getContentComponent().loginAccount, Validators.required],
        userId: [this.modalRef.getContentComponent().userId],
      })
    } else {
      this.isEdit = false;
      this.validateForm = this.fb.group({
        method: [null, [Validators.required]],
        loginAccount: [null, Validators.required],
        userId: [this.modalRef.getContentComponent().userId],
      })
    }
  }

  submitForm() {
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      this.buttonLoading = true;
      this.validateForm.value['userId'] = this.userId;
      if (!this.isEdit) {
        this.http.post('account', this.validateForm.value).then(d => {
          this.buttonLoading = false;
          if (d.success) {
            this.message.success(d.message);
            this.modalRef.close(true);
          } else {
            this.message.error(d.message);
          }
        }, e => {
          console.debug(e);
          this.message.success('服务器异常');
        })
      }
      else {
        this.http.put('account', this.validateForm.value).then(d => {
          this.buttonLoading = false;
          if (d.success) {
            this.message.success(d.message);
            this.modalRef.close(true);
          } else {
            this.message.error(d.message);
          }
        })
      }
    }
  }

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
  }

}
