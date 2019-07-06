import {Component, OnInit} from '@angular/core';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LocalStorageProvider} from '../../../service/local-storage';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;
  teacher;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef, private local: LocalStorageProvider) {
  }

  ngOnInit() {
    this.teacher = this.local.get("user");
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      teacherId: [this.teacher.id],
      courseName: [null, [Validators.required]],
      startWeek: [0, [Validators.required]],
      endWeek: [0, [Validators.required,]],
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
      if (this.validateForm.value.startWeek > this.validateForm.value.endWeek) {
        this.message.error('结束周必须不小于起始周！');
      } else {
        this.buttonLoading = true;
        this.http.post('course', this.validateForm.value).then((d) => {
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

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
  }
}
