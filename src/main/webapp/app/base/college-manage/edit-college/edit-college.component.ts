import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-edit-college',
  templateUrl: './edit-college.component.html',
  styleUrls: ['./edit-college.component.css']
})
export class EditCollegeComponent implements OnInit {

  private validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    this.validateForm = this.fb.group({
      id:[this.modalRef.getContentComponent().id],
      schoolId: [this.modalRef.getContentComponent().schoolId],
      collegeName: [this.modalRef.getContentComponent().collegeName, [Validators.required]],
      collegeAddress: [this.modalRef.getContentComponent().collegeAddress, [Validators.required]],
      collegeDetail: [this.modalRef.getContentComponent().collegeDetail],
    });
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
      this.http.put('college', this.validateForm.value).then((d) => {
        this.buttonLoading = false;
        if (d.success) {
          this.message.success(d.result);
          this.modalRef.close(true);
        } else {
          this.message.error(d.result);
        }
      }, e => {
        console.debug(e);
      });
    }
  }

}
