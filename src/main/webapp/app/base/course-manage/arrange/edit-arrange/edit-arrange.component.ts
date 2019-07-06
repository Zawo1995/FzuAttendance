import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {BaidumapComponent} from '../../../../util/baidumap/baidumap.component';

@Component({
  selector: 'app-edit-arrange',
  templateUrl: './edit-arrange.component.html',
  styleUrls: ['./edit-arrange.component.css']
})
export class EditArrangeComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService, private modalService: NzModalService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      id: [this.modalRef.getContentComponent().id],
      courseId: [this.modalRef.getContentComponent().courseId],
      courseAddress: [this.modalRef.getContentComponent().courseAddress, [Validators.required]],
      setRow: [this.modalRef.getContentComponent().setRow, [Validators.required]],
      setColumn: [this.modalRef.getContentComponent().setColumn, [Validators.required,]],
      dayNum: [this.modalRef.getContentComponent().dayNum, [Validators.required,]],
      lessonBegin: [this.modalRef.getContentComponent().lessonBegin, [Validators.required,]],
      lessonLength: [this.modalRef.getContentComponent().lessonLength, [Validators.required,]],
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
      if (this.validateForm.value.lessonBegin + this.validateForm.value.lessonLength - 1 > 11) {
        this.message.error('上课时间超出上限');
      } else {
        this.buttonLoading = true;
        this.http.put('arrange', this.validateForm.value).then((d) => {
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

  openBaiduMap() {
    this.modalService.create({
      nzTitle: null,
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: BaidumapComponent,
      nzWidth: '80%'
    });
  }
}
