import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { Observable } from 'rxjs';
@Injectable()
export class VisitorGuard implements CanActivate {

  constructor(private userService: UserService) {
  }

  canActivate(): Observable<boolean>|boolean {
    return Observable.create( observer => {
        this.userService.getUser().then(
          user => {
            if (user.isVisitor()) {
              observer.next(true);
            } else {
              observer.next(false);
            }
            observer.complete();
          },
          () => {
            observer.next(false);
            observer.complete();
          });
    });
  }
}
