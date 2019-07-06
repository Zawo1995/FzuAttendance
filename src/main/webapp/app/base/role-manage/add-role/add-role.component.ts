import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.css']
})
export class AddRoleComponent implements OnInit {

  oldName:string;
  validateForm: FormGroup;
  buttonLoading = false;
  isAdd = true;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) { }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    if(this.modalRef.getContentComponent().id != null){//编辑Role
      this.isAdd = false;
      this.oldName = this.modalRef.getContentComponent().name;//获取老的角色名称
      this.validateForm = this.fb.group({
        id:[this.modalRef.getContentComponent().id],
        name:[this.modalRef.getContentComponent().name,[Validators.required]],
        remark:[this.modalRef.getContentComponent().remark],
      });
    }else {//新增Role
      this.isAdd = true;
      this.validateForm = this.fb.group({
        name: [null, [Validators.required]],
        remark: [''],
      });
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
      if(this.isAdd){
        this.http.post('role', this.validateForm.value).then((d) => {
          this.buttonLoading = false;
          if (d.success) {
            this.message.success(d.message);
            this.modalRef.close(d.data);//关闭时返回新增的id
          } else {
            this.message.error(d.message);
          }
        }, e => {
          console.debug(e);
        });
      }else {
        this.validateForm.value['oldName'] = this.oldName;
        this.http.put('role', this.validateForm.value).then((d) => {
          this.buttonLoading = false;
          if (d.success) {
            this.message.success(d.message);
            this.modalRef.close(true);
          } else {
            this.message.error(d.message);
          }
        }, e => {
          console.debug(e);
        });
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
