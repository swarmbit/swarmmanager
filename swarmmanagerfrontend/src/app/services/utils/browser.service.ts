import { Injectable } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';

@Injectable()
export class BrowserService {

  private history: string[] = [];
  private previousUrl: string;
  private backUrl: string;

  constructor(private router: Router) {
    this.router.events
      .subscribe(event => {
        if (event instanceof NavigationEnd) {
          // it does not add this url, otherwise it would be cyclic
          if (this.previousUrl != this.backUrl) {
            this.history.push(this.previousUrl);
          } else if (this.previousUrl != null) {
            this.history.pop();
          }

          this.previousUrl = event.url;
          if (!this.isEmpty()) {
            // gets the latest url from the history
            this.backUrl = this.history[this.history.length - 1];
          }
        }
      });
  }

  isEmpty(): boolean {
    return this.history.length == 0;
  }

  getBackUrl(): string {
    return this.backUrl;
  }

  reset(): void {
    this.history = [];
  }

}
