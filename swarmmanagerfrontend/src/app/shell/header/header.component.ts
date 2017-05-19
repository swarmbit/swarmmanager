
import { Component } from '@angular/core';
import { RoutingService } from '../../routing/routing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  styleUrls: ['header.component.scss'],
  templateUrl: 'header.component.html'
})
export class HeaderComponent {

  constructor(private routingService: RoutingService,
                private router: Router) {
  }

  getHeaderName(): string {
    return this.routingService.getHeaderName();
  }

  showBackArrow() {
    let cssClass = 'material-icons arrow-back';

    if (this.routingService.getBackArrow() && this.routingService.getBackArrow().show) {
      cssClass += ' show';
    }
    return cssClass;
  }

  goBack() {
    if (this.routingService.getBackArrow()) {
      this.router.navigate([this.routingService.getBackArrow().link]);
    }
  }
}
