import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { UserService } from '../services/user/user.service';
import { Observable } from 'rxjs';
@Injectable()
export class AdminGuard implements CanActivate {

  constructor(private userService: UserService) {
  }

  canActivate(): Observable<boolean>|boolean {
    return Observable.create( observer => {
        this.userService.getUser().then(
          user => {
            if (user.isAdmin()) {
              observer.next(true);
            } else {
              observer.next(false);
            }
            observer.complete();
          });
    });
  }
}
