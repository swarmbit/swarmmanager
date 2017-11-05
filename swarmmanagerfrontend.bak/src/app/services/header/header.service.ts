import { Injectable } from '@angular/core';
import { HeaderInfo } from './header.info';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

@Injectable()
export class HeaderService {

  private headerInfoObservable: Observable<HeaderInfo>;
  private headerInfoObservers: Observer<HeaderInfo>[];

  constructor() {
    this.headerInfoObservers = [];
    this.headerInfoObservable = new Observable<HeaderInfo>(observer => {
      this.headerInfoObservers.push(observer);
    });
  }

  public getHeaderInfo(): Observable<HeaderInfo> {
    return this.headerInfoObservable;
  }

  public setHeaderInfo(headerInfo: HeaderInfo): void {
    for (let i = 0; i < this.headerInfoObservers.length; i++) {
      this.headerInfoObservers[i].next(headerInfo);
    }
  }

}
