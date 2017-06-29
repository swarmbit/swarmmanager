import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  styleUrls: ['login.component.css'],
  templateUrl: 'login.component.html'
})
export class LoginComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  login() {
    this.authService.login();
    this.router.navigate(['']);
  }

}
