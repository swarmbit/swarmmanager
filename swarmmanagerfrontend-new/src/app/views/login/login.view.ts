import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AuthError } from '../../services/auth/auth.error';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  styleUrls: ['login.view.scss'],
  templateUrl: 'login.view.html'
})
export class LoginView {

  generalErrorMessage = 'There was an error, if the problem persists please contact the system administration.';
  invalidUserPassword = 'Please insert a valid username and password.';
  currentErrorMessage = '';
  username: string;
  password: string;

  constructor(private authService: AuthService) {
  }

  login() {
    this.authService.login(this.username, this.password).catch(
      (authError: AuthError) => {
        if (authError === AuthError.FORBIDDEN || authError === AuthError.UNAUTHORIZED) {
          this.currentErrorMessage = this.invalidUserPassword;
        } else {
          this.currentErrorMessage = this.generalErrorMessage;
        }
      }
    );
  }

  onKeydown(event): void {
    if (event.key === 'Enter') {
      this.login();
    }
  }
}
