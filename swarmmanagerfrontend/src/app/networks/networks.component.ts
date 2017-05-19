
import { Component, OnInit } from '@angular/core';
import { RoutingService } from '../routing/routing.service';

@Component({
  selector: 'app-networks',
  templateUrl: 'networks.component.html'
})
export class NetworksComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Networks');
  }

}
