
import { Component, OnInit } from '@angular/core';
import { RoutingService } from '../routing/routing.service';

@Component({
  selector: 'app-registries',
  templateUrl: 'registries.component.html'
})
export class RegistriesComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Registries');
  }

}
