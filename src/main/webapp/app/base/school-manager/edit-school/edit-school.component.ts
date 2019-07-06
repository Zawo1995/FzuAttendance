import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-edit-school',
  templateUrl: './edit-school.component.html',
  styleUrls: ['./edit-school.component.css']
})
export class EditSchoolComponent implements OnInit {
  private validateForm: FormGroup;
  buttonLoading = false;
  oldName;


  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.oldName = this.modalRef.getContentComponent().schoolName;
    this.validateForm = this.fb.group({
      id:[this.modalRef.getContentComponent().id],
      schoolName: [this.modalRef.getContentComponent().schoolName, [Validators.required]],
      schoolAddress: [this.modalRef.getContentComponent().schoolAddress, [Validators.required]],
      schoolDetail: [this.modalRef.getContentComponent().schoolDetail],
    });
  }

  submitForm() {
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.validateForm.value['oldSchoolName'] = this.oldName;
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      this.buttonLoading = true;
      this.http.put('school', this.validateForm.value).then((d) => {
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
