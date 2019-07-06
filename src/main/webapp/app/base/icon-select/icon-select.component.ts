import {Component, OnInit} from '@angular/core';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-icon-select',
  templateUrl: './icon-select.component.html',
  styleUrls: ['./icon-select.component.css']
})
export class IconSelectComponent implements OnInit {

  selectedType = "方向性图标";

  types = [
    '方向性图标',
    '提示建议性图标',
    '编辑类图标',
    '数据类图标',
    '品牌和标识',
    '网站通用图标',
  ]

  icons: { [key: string]: Array<String> } = {
    '方向性图标': ['step-backward', 'step-forward', 'fast-backward', 'fast-forward', 'shrink', 'arrows-alt',
                  'down', 'up-circle', 'fullscreen', 'play-circle', 'pic-right', 'fullscreen-exit'],
    '提示建议性图标': ['question', 'question-circle','plus','plus-circle','pause','pause-circle'],
    '编辑类图标': ['edit', 'form'],
    '数据类图标': ['area-chart', 'pie-chart'],
    '品牌和标识': ['android', 'apple'],
    '网站通用图标': ['account-book', 'book'],
  }


  constructor(private modalRef: NzModalRef) {
  }

  ngOnInit() {
  }

  selectIcon(iconName) {
    this.modalRef.close(iconName);
  }

}
