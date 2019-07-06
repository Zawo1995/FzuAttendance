import { Injectable } from '@angular/core';
import {Subject} from 'rxjs/index';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private messageSu = new Subject<string>(); //
  messageObserve = this.messageSu.asObservable();
  constructor() { }

  private setMessage(message: any) {
    this.messageSu.next(message);
  }
  public success(message: any) {
    this.setMessage(message);
  }
}
