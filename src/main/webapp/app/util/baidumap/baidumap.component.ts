import {Component, OnInit} from '@angular/core';
import {MapOptions} from 'angular2-baidu-map';
declare var BMap: any;

@Component({
  selector: 'app-baidumap',
  templateUrl: './baidumap.component.html',
  styleUrls: ['./baidumap.component.css']
})
export class BaidumapComponent implements OnInit {

  opts: MapOptions;

  constructor() {
  }

  ngOnInit() {
    this.opts = {
      centerAndZoom: {
        lng: 119.203482,
        lat: 26.062197,
        zoom: 13
      },
      enableKeyboard: true,
      enableScrollWheelZoom: true, // 是否启用滚轮进行缩放功能
      currentCity: '福州市'
    }
  }

}
