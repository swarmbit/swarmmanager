import { Component, OnInit } from '@angular/core';
import { RoutingService } from '../routing/routing.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Dashboard');
  }

}
