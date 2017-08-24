
import { Component, OnInit } from '@angular/core';
import { User } from './user';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-user',
  styleUrls: ['user.component.css'],
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {

  user: User;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user = new User();
    this.user.name = 'Bruno Vale';
  }

  logout() {
    this.authService.logout();
  }

}
