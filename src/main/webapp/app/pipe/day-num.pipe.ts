import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dayNum'
})
export class DayNumPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    let chineseDayNum;
    switch(value){
      case 1:chineseDayNum = '星期一';break;
      case 2:chineseDayNum = '星期二';break;
      case 3:chineseDayNum = '星期三';break;
      case 4:chineseDayNum = '星期四';break;
      case 5:chineseDayNum = '星期五';break;
      case 6:chineseDayNum = '星期六';break;
      case 7:chineseDayNum = '星期日';break;
    }
    return chineseDayNum;
  }

}
