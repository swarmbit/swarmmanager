import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  styleUrls: ['login.view.css'],
  templateUrl: 'login.view.html'
})
export class LoginView {

  generalErrorMessage = 'There was an error, if the problem persist please contact the System Administration!';
  invalidUserPassword = 'Please insert a valid username and password!';
  currentErrorMessage = this.invalidUserPassword;
}
