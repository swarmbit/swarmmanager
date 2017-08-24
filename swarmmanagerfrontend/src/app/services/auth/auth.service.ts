import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { UserCredentials } from './model/user.credentials';
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthService {

  public static AUTH_HEADER = 'Authorization';

  public static AUTH_USER = 'User';

  private loginUrl = '/api/auth/login';

  private logoutUrl = '/api/auth/logout';

  constructor (private http: Http) {}

  isAuthenticated(): boolean {
    const token = localStorage.getItem(AuthService.AUTH_HEADER);
    if (token && token != null) {
      return true;
    }
    return false;
  }

  login(username: string, password: string): void {
    const userCredentials = new UserCredentials();
    userCredentials.setUsername(username);
    userCredentials.setPassword(password);
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions(headers);
    this.http.post(this.loginUrl, userCredentials, options)
      .map((response) => {
        localStorage.setItem(AuthService.AUTH_HEADER, response.headers.get(AuthService.AUTH_HEADER));
        localStorage.setItem(AuthService.AUTH_USER, userCredentials.getUsername());
      }).catch((error: any) => Observable.throw(error.json().error || 'Server error'))
      .subscribe();
  }

  logout(): void  {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions(headers);
    const user: string = localStorage.getItem(AuthService.AUTH_USER);
    console.log(user);
    if (user && user != null) {
      const userCredentials: UserCredentials = new UserCredentials();
      userCredentials.setUsername(user);
      this.http.post(this.logoutUrl, userCredentials, options)
        .map(() => {
          localStorage.removeItem(AuthService.AUTH_HEADER);
          localStorage.removeItem(AuthService.AUTH_USER);
        })
        .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
        .subscribe();
    }
  }

}
