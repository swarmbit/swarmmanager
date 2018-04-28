  import {
  HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProgressBarService } from '../services/progress.bar/progress.bar.service';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/do';

@Injectable()
export class ProgressBarInterceptor implements HttpInterceptor {

  constructor(public progressBarService: ProgressBarService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.progressBarService.showProgessBar();
    return next.handle(req).do(event => {
      if (event instanceof HttpResponse) {
        setTimeout(() => {
          this.progressBarService.hideProgessBar();
        }, 700);
      }
    }).catch(
      (err) => {
        setTimeout(() => {
          this.progressBarService.hideProgessBar();
        }, 700);
        return Observable.throw(err);
      });
  }

}
