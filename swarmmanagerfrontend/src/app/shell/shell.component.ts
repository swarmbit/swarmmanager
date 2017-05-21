import { Component } from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-shell',
  styleUrls: ['shell.component.scss'],
  templateUrl: 'shell.component.html'
})
export class ShellComponent {

  constructor(private authService: AuthService) {
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  getPageClass() {
    if (this.authService.isAuthenticated()) {
      return 'page';
    } else {
      return '';
    }
  }
}
