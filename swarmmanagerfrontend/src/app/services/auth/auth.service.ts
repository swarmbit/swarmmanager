import { Injectable } from '@angular/core';
import { UserCredentials } from './model/user.credentials';
import 'rxjs/add/operator/catch';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class AuthService {

  public static AUTH_HEADER = 'Authorization';

  public static AUTH_USER = 'User';

  private loginUrl = '/api/auth/login';

  private logoutUrl = '/api/auth/logout';

  static isAuthenticated(): boolean {
    const token = localStorage.getItem(AuthService.AUTH_HEADER);
    if (token && token != null) {
      return true;
    }
    return false;
  }

  static removeToken(): void {
    const token = localStorage.getItem(AuthService.AUTH_HEADER);
    if (token && token != null) {
      localStorage.removeItem(AuthService.AUTH_HEADER);
    }
  }

  constructor (private http: HttpClient) {
  }

  login(username: string, password: string): void {
    const userCredentials = new UserCredentials();
    userCredentials.setUsername(username);
    userCredentials.setPassword(password);
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.http.post(this.loginUrl, userCredentials, {
      observe: 'response',
      headers: headers
    })
      .map((response) => {
        localStorage.setItem(AuthService.AUTH_HEADER, response.headers.get(AuthService.AUTH_HEADER));
        localStorage.setItem(AuthService.AUTH_USER, userCredentials.getUsername());
      }).subscribe();
  }

  logout(): void  {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const user: string = localStorage.getItem(AuthService.AUTH_USER);
    if (user && user != null) {
      const userCredentials: UserCredentials = new UserCredentials();
      userCredentials.setUsername(user);
      this.http.post(this.logoutUrl, userCredentials, {
        headers: headers
      })
        .map(() => {
          localStorage.removeItem(AuthService.AUTH_HEADER);
          localStorage.removeItem(AuthService.AUTH_USER);
        }).subscribe();
    }
  }

}
