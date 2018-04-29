import { Injectable } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';

@Injectable()
export class BrowserService {

  private backUrl: string;
  private currentUrl: string;
  private backEnabled: boolean;

  constructor(private router: Router) {
    this.router.events
      .subscribe(event => {
        if (event instanceof NavigationEnd) {
          if (this.currentUrl != null) {
            this.backUrl = this.currentUrl;
            this.currentUrl = event.url;
          } else {
          this.currentUrl = event.url;
          }
        }
      });
  }

  cannotGoBack(): boolean {
    return this.backUrl == null;
  }

  getBackUrl(): string {
    return this.backUrl;
  }

  getCurrentUrl(): string {
    return this.currentUrl;
  }

  reset(): void {
    this.currentUrl = null;
    this.backUrl = null;
  }

}
