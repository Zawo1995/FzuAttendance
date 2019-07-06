import {Component, OnInit} from '@angular/core';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HttpServiceProvider} from '../../../service/service-http';

@Component({
  selector: 'app-sign-detail',
  templateUrl: './sign-detail.component.html',
  styleUrls: ['./sign-detail.component.less']
})
export class SignDetailComponent implements OnInit {

  signId;
  courseId;
  signList = [];
  absentList = [];
  tabIndex = 0;

  constructor(private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  ngOnInit() {
    console.log(this.modalRef.getContentComponent());
    this.signId = this.modalRef.getContentComponent().signId;
    this.courseId = this.modalRef.getContentComponent().courseId;
    this.refresh();

  }

  refresh() {
    //获得全部签到信息
    this.http.get('signDetail/detail', {signId: this.signId}).then(d => {
      if (d.success) {
        this.signList = d.data;
        //获得改班课全部学生信息
        this.http.get("studentCourse", {courseId: this.courseId}).then(d2 => {
          if (d2.success) {
            this.getAbsentList(d2.data);
          } else {
            this.message.error(d2.message);
          }
          console.log(d2);
        })
      } else {
        this.message.error(d.message);
      }
      console.log(d);
    })

  }

  //获得未签到学生的列表
  getAbsentList(data) {
    let list = [];
    for (let i of data) {
      let userId = i.studentId;
      if (this.signList.filter(item => item.userId == userId).length == 0) {
        list.push({
          userId: userId,
          name: i.name,
          schoolName: i.schoolName,
          collegeName: i.collegeName,
          phone: i.phone
        })
      }
    }
    this.absentList = list;
    console.log(this.absentList);
  }

}
