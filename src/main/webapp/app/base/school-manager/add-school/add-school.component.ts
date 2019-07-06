import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-add-school',
  templateUrl: './add-school.component.html',
  styleUrls: ['./add-school.component.css']
})
export class AddSchoolComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    this.validateForm = this.fb.group({
      schoolName: [null, [Validators.required]],
      schoolAddress: [null, [Validators.required]],
      schoolDetail: [''],
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
      this.http.post('school', this.validateForm.value).then((d) => {
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

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
  }


}
