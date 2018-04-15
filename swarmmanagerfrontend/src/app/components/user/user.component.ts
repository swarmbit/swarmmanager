
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user/user.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  styleUrls: ['user.component.css'],
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit, OnDestroy {

  displayName: String;

  subs: Subscription[] = [];

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.subs.push(this.userService.onUserChange().subscribe(
      user => {
        this.displayName = user.displayName;
      }
    ));
  }

  logout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
