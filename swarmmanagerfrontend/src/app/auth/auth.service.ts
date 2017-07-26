import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { UserCredentials } from './user.credentials';
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthService {

  public static AUTH_HEADER = 'Authorization';

  private loginUrl = '/api/auth/login';

  private logoutUrl = '/api/auth/logout';

  private currentUser: UserCredentials;

  private authenticated = false;

  constructor (private http: Http) {}

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  login(userCredentials: UserCredentials): void {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions(headers);
    this.http.post(this.loginUrl, userCredentials, options)
      .map((response) => {
        localStorage.setItem(AuthService.AUTH_HEADER, response.headers.get(AuthService.AUTH_HEADER));
        this.authenticated = true;
        const currentUser = new UserCredentials();
        currentUser.setUsername(userCredentials.getUsername());
        this.currentUser = currentUser;
      }).catch((error: any) => Observable.throw(error.json().error || 'Server error'))
      .subscribe();
  }

  logout(): void  {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions(headers);
    this.http.post(this.logoutUrl, this.currentUser, options)
      .map(() => {
        localStorage.removeItem(AuthService.AUTH_HEADER);
        this.authenticated = false;
        this.currentUser = null;
      })
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
      .subscribe();
  }

}
