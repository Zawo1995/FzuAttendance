import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-add-college',
  templateUrl: './add-college.component.html',
  styleUrls: ['./add-college.component.css']
})
export class AddCollegeComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      schoolId: [this.modalRef.getContentComponent().id],
      collegeName: [null, [Validators.required]],
      collegeAddress: [null, [Validators.required]],
      collegeDetail: [''],
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
      this.http.post('college', this.validateForm.value).then((d) => {
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

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
  }

}
