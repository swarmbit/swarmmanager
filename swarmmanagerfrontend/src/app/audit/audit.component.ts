import { Component, OnInit } from '@angular/core';
import { RoutingService } from '../routing/routing.service';

@Component({
  selector: 'app-audit',
  templateUrl: 'audit.component.html'
})
export class AuditComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Audit');
  }

}
