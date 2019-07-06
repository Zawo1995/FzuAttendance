import {Component, OnInit} from '@angular/core';
import {HttpServiceProvider} from '../../../service/service-http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-rest-time',
  templateUrl: './rest-time.component.html',
  styleUrls: ['./rest-time.component.css']
})
export class RestTimeComponent implements OnInit {

  constructor(private http: HttpServiceProvider, private message: NzMessageService,
              private modalRef: NzModalRef) {
  }

  lessonDelayFromDB = 0;//来自数据库的delay
  lessonDelay: number = 0;//作息调整
  lessonTime: number = 0;
  breakTime: number = 0;
  data = {
    lessonMorning: <any>[],
    lessonAfternoon: <any>[],
    lessonNight: <any>[],
  };
  loading = false;

  ngOnInit() {
    console.debug(this.modalRef.getContentComponent());
    this.lessonTime = this.modalRef.getContentComponent().lessonTime;
    this.breakTime = this.modalRef.getContentComponent().breakTime;
    this.lessonDelay = this.modalRef.getContentComponent().lessonDelay;
    this.data.lessonMorning.push({
      lessonMsg: '第一节课',
      lessonWhen: this.modalRef.getContentComponent().lessonOne
    });
    this.data.lessonMorning.push({
      lessonMsg: '第二节课',
      lessonWhen: this.modalRef.getContentComponent().lessonTwo
    });
    this.data.lessonMorning.push({
      lessonMsg: '第三节课',
      lessonWhen: this.modalRef.getContentComponent().lessonThree
    });
    this.data.lessonMorning.push({
      lessonMsg: '第四节课',
      lessonWhen: this.modalRef.getContentComponent().lessonFour
    });
    this.data.lessonAfternoon.push({
      lessonMsg: '第五节课',
      lessonWhen: this.modalRef.getContentComponent().lessonFive,
    });
    this.data.lessonAfternoon.push({
      lessonMsg: '第六节课',
      lessonWhen: this.modalRef.getContentComponent().lessonSix,
    });
    this.data.lessonAfternoon.push({
      lessonMsg: '第七节课',
      lessonWhen: this.modalRef.getContentComponent().lessonSeven,
    });
    this.data.lessonAfternoon.push({
      lessonMsg: '第八节课',
      lessonWhen: this.modalRef.getContentComponent().lessonEight,
    });
    this.data.lessonNight.push({
      lessonMsg: '第九节课',
      lessonWhen: this.modalRef.getContentComponent().lessonNine,
    });
    this.data.lessonNight.push({
      lessonMsg: '第十节课',
      lessonWhen: this.modalRef.getContentComponent().lessonTen,
    });
    this.data.lessonNight.push({
      lessonMsg: '第十一节课',
      lessonWhen: this.modalRef.getContentComponent().lessonEleven,
    });
    console.debug(this.data);
  }

  isUpdate = false;

  update() {
    if (this.isUpdate) {
      let data = {
        id: this.modalRef.getContentComponent().id,
        schoolId: this.modalRef.getContentComponent().schoolId,
        lessonTime: this.lessonTime,
        breakTime: this.breakTime,
        lessonDelay: this.lessonDelay,
        lessonOne: this.data.lessonMorning[0].lessonWhen,
        lessonTwo: this.data.lessonMorning[1].lessonWhen,
        lessonThree: this.data.lessonMorning[2].lessonWhen,
        lessonFour: this.data.lessonMorning[3].lessonWhen,
        lessonFive: this.data.lessonAfternoon[0].lessonWhen,
        lessonSix: this.data.lessonAfternoon[1].lessonWhen,
        lessonSeven: this.data.lessonAfternoon[2].lessonWhen,
        lessonEight: this.data.lessonAfternoon[3].lessonWhen,
        lessonNine: this.data.lessonNight[0].lessonWhen,
        lessonTen: this.data.lessonNight[1].lessonWhen,
        lessonEleven: this.data.lessonNight[2].lessonWhen,
      }
      console.debug(data);
      this.loading = true;
      this.http.put('restTime', data).then(d => {
        console.debug(d);
        this.loading = false;
        if (d.success) {
          this.message.success(d.result);
          this.isUpdate = false;
        } else {
          this.message.error(d.result);
        }
      }, e => {
        console.debug(e);
      })
    } else {
      this.isUpdate = true;
    }
  }

}
