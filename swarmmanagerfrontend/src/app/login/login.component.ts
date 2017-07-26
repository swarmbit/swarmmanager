import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { UserCredentials } from '../auth/user.credentials';
@Component({
  selector: 'app-login',
  styleUrls: ['login.component.css'],
  templateUrl: 'login.component.html'
})
export class LoginComponent {

  username: string;

  password: string;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    const user = new UserCredentials();
    user.setUsername(this.username);
    user.setPassword(this.password);
    this.authService.login(user);
    this.router.navigate(['']);
  }

}
