import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { UserService } from '../services/user/user.service';
import { Observable } from 'rxjs';
@Injectable()
export class LoginGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(): Observable<boolean>|boolean {
    return Observable.create( observer => {
      if (AuthService.isAuthenticated()) {
        this.userService.getUser().then(
          user => {
            if (user.isVisitor()) {
              this.router.navigate(['']);
            } else {
              this.router.navigate(['user/profile']);
            }
          });
        observer.next(false);
        observer.complete();
      }
      observer.next(true);
      observer.complete();
    });
  }
}
