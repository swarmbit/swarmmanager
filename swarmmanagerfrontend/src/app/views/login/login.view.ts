import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  styleUrls: ['login.view.css'],
  templateUrl: 'login.view.html'
})
export class LoginView {

  username: string;

  password: string;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.username, this.password);
    this.router.navigate(['']);
  }

}
