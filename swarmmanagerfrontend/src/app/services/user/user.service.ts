import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { User } from './model/user';
@Injectable()
export class UserService {

  private userDataUrl = '/api/userData';

  private user: User;

  constructor (private http: HttpClient) {
  }

  getUser(fetch: boolean): Promise<User> {
    return new Promise((resolve, reject) => {
      if (this.user == null || fetch) {
        this.fetchData().then(
          user => {
            this.user = user;
            resolve(user);
          }
        ).catch(
          () => {
            reject();
          }
        );
      } else {
        resolve(this.user);
      }
    });
  }

  clearUser(): void {
    this.user = null;
  }

  private fetchData(): Promise<User> {
    return new Promise((resolve, reject) => {
      this.http.get(this.userDataUrl)
        .subscribe(
          (data: any) => {
            const user = new User;
            user.username = data.username;
            user.displayName = data.displayName;
            user.roles = data.roles;
            resolve(user);
          },
          (err: HttpErrorResponse) => {
            console.log('Error fetching user data: ' + err.message);
            reject();
          }
        );
    });
  }

}

