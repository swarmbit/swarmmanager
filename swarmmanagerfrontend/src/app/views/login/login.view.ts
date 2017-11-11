import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AuthError } from '../../services/auth/model/auth.error';

@Component({
  selector: 'app-login',
  styleUrls: ['login.view.scss'],
  templateUrl: 'login.view.html'
})
export class LoginView {

  generalErrorMessage = 'There was an error, if the problem persist please contact the System Administration!';
  invalidUserPassword = 'Please insert a valid username and password!';
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
}
