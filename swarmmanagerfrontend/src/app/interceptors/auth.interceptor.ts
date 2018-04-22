import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (AuthService.isAuthenticated()) {
      req = req.clone({
        headers: req.headers.set(AuthService.AUTH_HEADER, localStorage.getItem(AuthService.AUTH_HEADER))});
    }
    return next.handle(req).catch(
      err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401 || err.status === 403) {
          AuthService.removeToken();
          this.router.navigate(['/login']);
        }
        return Observable.throw(err);
      }
    });
  }
}
