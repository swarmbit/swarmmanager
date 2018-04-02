import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { User } from './model/user';
import { SnackbarService } from '../snackbar/snackbar.service';
@Injectable()
export class UserService {

  private userDataUrl = '/api/userData';

  private user: User;

  constructor (private http: HttpClient, private snackbarService: SnackbarService) {
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
            this.snackbarService.showError('Error loading user data, if the problem persists please contact the Administrator!');
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
            this.snackbarService.showError('Error loading user data, if the problem persists please contact the Administrator!');
            console.log('Error fetching user data: ' + err.message);
            reject();
          }
        );
    });
  }

}

