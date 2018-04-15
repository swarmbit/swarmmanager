
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../user/user';
import { CreateUserResponse } from './create.user.response';

@Injectable()
export class UsersService {

  private usersUrl = '/api/users';

  constructor (private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  removeUser(username: string): Observable<any> {
    return this.http.delete(this.usersUrl + '/' + username);
  }

  changeRole(username: string, role: string): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(this.usersUrl + '/' + username + '/changeRole', {
      role: role
    }, {
      observe: 'response',
      headers: headers,
      responseType: 'text'
    });
  }

  resetPassword(username: string, resetKey: string, password: string): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(this.usersUrl + '/resetPassword', {
      username: username,
      resetKey: btoa(resetKey),
      password: btoa(password)
    }, {
      observe: 'response',
      headers: headers,
      responseType: 'text'
    });
  }

  createUser(username: string, password: string): Observable<CreateUserResponse> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<CreateUserResponse>(this.usersUrl + '/create', {
      username: username,
      password: btoa(password)
    }, {
      headers: headers
    });
  }

}
