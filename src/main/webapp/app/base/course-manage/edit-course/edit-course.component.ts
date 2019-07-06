import {Component, OnInit} from '@angular/core';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.css']
})
export class EditCourseComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      id: [this.modalRef.getContentComponent().id],
      teacherId: [this.modalRef.getContentComponent().teacherId],//暂时
      courseName: [this.modalRef.getContentComponent().courseName, [Validators.required]],
      startWeek: [this.modalRef.getContentComponent().startWeek, [Validators.required]],
      endWeek: [this.modalRef.getContentComponent().endWeek, [Validators.required]],
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
      if(this.validateForm.value.startWeek > this.validateForm.value.endWeek){
        this.message.error('结束周必须不小于起始周！');
      }else{
        this.buttonLoading = true;
        this.http.put('course', this.validateForm.value).then((d) => {
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


}
