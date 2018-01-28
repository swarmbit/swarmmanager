import { Injectable } from '@angular/core';
import { UserCredentials } from './model/user.credentials';
import 'rxjs/add/operator/catch';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthError } from './model/auth.error';
import { UserService } from '../user/user.service';

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

  constructor (private http: HttpClient, private userService: UserService) {
  }

  login(username: string, password: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const userCredentials = new UserCredentials();
      userCredentials.setUsername(username);
      userCredentials.setPassword(password);
      const headers = new HttpHeaders().set('Content-Type', 'application/json');

      this.http.post(this.loginUrl, userCredentials, {
        observe: 'response',
        headers: headers,
        responseType: 'text'
      }).subscribe(
        (resp: HttpResponse<any>) => {
          localStorage.setItem(AuthService.AUTH_HEADER, resp.headers.get(AuthService.AUTH_HEADER));
          localStorage.setItem(AuthService.AUTH_USER, userCredentials.getUsername());
          resolve();
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            reject(AuthError.GENERAL);
          } else {
            if (err.status == 401) {
              reject(AuthError.UNAUTHORIZED);
            } else if (err.status == 403) {
              reject(AuthError.FORBIDDEN);
            } else {
              reject(AuthError.GENERAL);
            }
          }
        }
      );
    });
  }

  logout(): void  {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const user: string = localStorage.getItem(AuthService.AUTH_USER);
    if (user && user != null) {
      const userCredentials: UserCredentials = new UserCredentials();
      userCredentials.setUsername(user);
      this.http.post(this.logoutUrl, userCredentials, {
        headers: headers
      }).subscribe(
        (resp: HttpResponse<any>) => {
          localStorage.removeItem(AuthService.AUTH_HEADER);
          localStorage.removeItem(AuthService.AUTH_USER);
          this.userService.clearUser();
        },
        (err: HttpErrorResponse) => {
          localStorage.removeItem(AuthService.AUTH_HEADER);
          localStorage.removeItem(AuthService.AUTH_USER);
          this.userService.clearUser();
        }
      );
    }
  }

}
