import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { SnackbarService } from '../snackbar/snackbar.service';
import { Observable, Observer } from 'rxjs';
import { Router } from '@angular/router';
import { ApiUtils } from '../utils/api.utils';

@Injectable()
export class UserService {

  private userDataUrl = '/api/user';

  private changeDisplayName = '/api/user/changeDisplayName';

  private changePasswordUrl = '/api/user/changePassword';

  private user: User;

  private userChangeObservers: Observer<User>[] = [];

  constructor (private http: HttpClient, private snackbarService: SnackbarService, private router: Router) {
  }

  onUserChange(): Observable<User> {
    return Observable.create(observer => {
      if (this.user != null) {
        observer.next(this.user);
      } else {
        this.getUser().then((user) => {
          observer.next(user);
        });
      }
      this.userChangeObservers.push(observer);
    });
  }

  getUser(): Promise<User> {
    return new Promise((resolve, reject) => {
      if (this.user == null) {
        this.fetchData().then(
          user => {
            this.user = user;
            resolve(user);
          }
        ).catch(
          (err) => {
            if (err.status != 401 && err.status != 403) {
              this.snackbarService.showError('Error loading user data, if the problem persists please contact the administrator.');
            }
            reject(err);
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

  setDisplayName(displayName: string): Observable<any> {
    return Observable.create(observer => {
      const headers = new HttpHeaders().set('Content-Type', 'application/json');
      this.http.post(this.changeDisplayName, {
        displayName: displayName,
      }, {
        observe: 'response',
        headers: headers,
        responseType: 'text'
      }).subscribe(
        (result) => {
          this.user.displayName = displayName;
          observer.next(result);
          observer.complete();
          this.notifyUserObservers();
          this.snackbarService.showSuccess('Display name saved');
        },
        error => {
          observer.error(error);
          observer.complete();
          this.snackbarService.showError('Failed to save display name');
        }
      );
    });
  }

  changePassword(currentPassword: string, newPassword: string): Observable<any> {
    return Observable.create(observer => {
      const headers = new HttpHeaders().set('Content-Type', 'application/json');
      return this.http.post(this.changePasswordUrl, {
        currentPassword: btoa(currentPassword),
        newPassword: btoa(newPassword),
      }, {
        observe: 'response',
        headers: headers,
        responseType: 'text'
      }).subscribe(
        () => {
          this.snackbarService.showSuccess('Password changed');
          observer.next();
          observer.complete();
        },
        error => {
          this.snackbarService.showSuccess('Failed to change password');
          const apiError = ApiUtils.getApiError(error);
          if (apiError != null) {
            observer.error(apiError);
            observer.complete();
            return apiError;
          }
          observer.error(error);
          observer.complete();
        }
      );
    });
  }

  private notifyUserObservers(): void {
    for (const userObserver of this.userChangeObservers) {
      if (this.user != null) {
        userObserver.next(this.user);
      }
    }
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
          (err: any) => {
            if (err) {
              console.log('Error fetching user data: ' + err.message);
            }
            reject(err);
          }
        );
    });
  }

}
