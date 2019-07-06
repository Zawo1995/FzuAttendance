import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {BaidumapComponent} from '../../../../util/baidumap/baidumap.component';

@Component({
  selector: 'app-add-arrange',
  templateUrl: './add-arrange.component.html',
  styleUrls: ['./add-arrange.component.css']
})
export class AddArrangeComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading = false;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService, private modalService: NzModalService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.validateForm = this.fb.group({
      courseId: [this.modalRef.getContentComponent().courseId],//暂时
      courseAddress: [null, [Validators.required]],
      setRow: [0, [Validators.required]],
      setColumn: [0, [Validators.required,]],
      dayNum: [1, [Validators.required,]],
      lessonBegin: [1, [Validators.required,]],
      lessonLength: [1, [Validators.required,]],
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
      if(this.validateForm.value.lessonBegin + this.validateForm.value.lessonLength - 1 > 11){
        this.message.error('上课时间超出上限');
      }else{
        this.buttonLoading = true;
        this.http.post('arrange', this.validateForm.value).then((d) => {
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

  openBaiduMap(){
    this.modalService.create({
      nzTitle:null,
      nzFooter:null,
      nzMaskClosable: false,
      nzContent: BaidumapComponent,
      nzWidth:'80%'
    });
  }

}
