
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user',
  styleUrls: ['user.component.css'],
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {

  userName: String;

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.userService.getUser(false).then(
      user => {
        this.userName = user.displayName;
      }
    );
  }

  logout() {
    this.authService.logout();
  }

}
