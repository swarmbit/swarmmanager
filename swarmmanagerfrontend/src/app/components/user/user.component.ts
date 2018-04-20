
import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user',
  styleUrls: ['user.component.css'],
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit, OnDestroy {

  displayName: String;

  subs: Subscription[] = [];

  @Output()
  menuClick: EventEmitter <boolean>;

  constructor(private userService: UserService, private authService: AuthService) {
    this.menuClick = new EventEmitter();
  }

  ngOnInit(): void {
    this.subs.push(this.userService.onUserChange().subscribe(
      user => {
        this.displayName = user.displayName;
      }
    ));
  }

  clickMenu() {
    this.menuClick.emit(true);
  }

  logout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
