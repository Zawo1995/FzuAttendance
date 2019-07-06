import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {LocalStorageProvider} from '../../../service/local-storage';
import {CanAuthService} from '../../../service/can-auth.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-lock',
  templateUrl: './lock.component.html',
  styleUrls: ['./lock.component.less']
})
export class LockComponent implements OnInit {

  validateForm: FormGroup;
  buttonLoading;
  error = '';
  user;

  constructor(private fb: FormBuilder, private http: HttpServiceProvider, private message: NzMessageService,
              private router: Router, private canAuth: CanAuthService, private local: LocalStorageProvider,
              private activateRoute: ActivatedRoute) { }

  ngOnInit() {
    this.user = this.local.get("user");
    this.validateForm = this.fb.group({
      password: [null, [Validators.required]],
    });
  }

  submit() {
    this.error = '';
    console.debug(this.validateForm);
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    console.debug(this.validateForm.value);
    if (this.validateForm.valid) {
      this.buttonLoading = true;
      if(this.user == null){
        this.router.navigateByUrl("/menus");//去执行守护路由
      }else {
        this.user['userName'] = this.user.phone;
        this.user['password'] = this.validateForm.value.password;
        this.http.post('loginout/login', this.user).then(d => {
          this.buttonLoading = false;
          if (d.success) {
            this.message.success(d.message);
            this.local.set('user', d.data);
            this.router.navigateByUrl("/menus");
          } else {
            this.error = d.message;
          }
          console.log(d);
        })
      }
    }
  }

}
