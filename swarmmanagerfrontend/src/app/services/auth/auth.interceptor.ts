import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './auth.service';

export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (AuthService.isAuthenticated()) {
      req = req.clone({
        headers: req.headers.set(AuthService.AUTH_HEADER, localStorage.getItem(AuthService.AUTH_HEADER))});
    }
    return next.handle(req).catch(err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401 || err.status === 403) {
          AuthService.removeToken();
        }
        return Observable.throw(err);
      }
    });
  }

}
