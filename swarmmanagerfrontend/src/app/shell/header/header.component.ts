
import {Component, OnDestroy} from '@angular/core';
import { HeaderService } from './header-service/header.service';
import { Router } from '@angular/router';
import {HeaderInfo} from './header-service/header.info';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-header',
  styleUrls: ['header.component.scss'],
  templateUrl: 'header.component.html'
})
export class HeaderComponent implements OnDestroy {

  headerInfo: HeaderInfo;
  selectedViewName: string;
  private subscription: Subscription;

  constructor(private routingService: HeaderService,
                private router: Router) {
    this.subscription = this.routingService.getHeaderInfo().subscribe(headerInfo => {
      this.headerInfo = headerInfo;
      this.selectedViewName = headerInfo.currentViewName;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getSelectedViewName() {
    if (this.headerInfo && this.headerInfo.currentViewName) {
      return this.headerInfo.currentViewName;
    }
    return '';
  }

  showBackArrow() {
    let cssClass = 'material-icons arrow-back';

    if (this.headerInfo && this.headerInfo.backArrow && this.headerInfo.backArrow.show) {
      cssClass += ' show';
    }
    return cssClass;
  }

  goBack() {
    if (this.headerInfo && this.headerInfo.backArrow) {
      this.router.navigate([this.headerInfo.backArrow.link]);
    }
  }
}
