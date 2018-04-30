import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Injectable()
export class BrowserService {

  private goingBack = false;
  private history = [];

  constructor(private router: Router) {
    this.router.events
      .subscribe(event => {
        if (event instanceof NavigationEnd) {
          if (!this.goingBack) {
            this.history.push(event.url);
          } else {
            this.history.pop();
            this.goingBack = false;
          }
        }
      });
  }

  cannotGoBack(): boolean {
    return this.history.length == 1;
  }

  goBack(): string {
    this.goingBack = true;
    if (this.history.length > 1) {
      return this.history[this.history.length - 2];
    }
    return null;
  }

  getPreviousUrl(): string {
    if (this.history.length > 1) {
      return this.history[this.history.length - 2];
    }
    return null;
  }

  getCurrentUrl(): string {
    if (this.history.length > 0) {
      return this.history[this.history.length - 1];
    }
    return null;
  }

  reset(): void {
    this.history = [];
  }

}
