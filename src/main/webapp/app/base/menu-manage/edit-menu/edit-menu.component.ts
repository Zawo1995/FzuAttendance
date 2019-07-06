import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {IconSelectComponent} from '../../icon-select/icon-select.component';

@Component({
  selector: 'app-edit-menu',
  templateUrl: './edit-menu.component.html',
  styleUrls: ['./edit-menu.component.css']
})
export class EditMenuComponent implements OnInit {

  private validateForm: FormGroup;
  buttonLoading = false;
  icon;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef, private modalService: NzModalService) {
  }

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.icon = this.modalRef.getContentComponent().icon;
    this.validateForm = this.fb.group({
      id: [this.modalRef.getContentComponent().id],
      name: [this.modalRef.getContentComponent().name, [Validators.required]],
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
      this.validateForm.value['icon'] = this.icon;
      this.http.put('menu', this.validateForm.value).then((d) => {
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

  openIconSelection() {
    let $this = this;
    let modal = this.modalService.create({
      nzTitle: '请选择图标',
      nzFooter: null,
      nzMaskClosable: false,
      nzContent: IconSelectComponent,
      nzWidth: '80%'
    })
    modal.afterClose.subscribe((icon) => {
      if (icon) {
        this.icon = icon;
        console.log(this.icon);
      }
    })
  }

}
