import { Component, OnInit } from '@angular/core';
import { RoutingService } from '../routing/routing.service';

@Component({
  selector: 'app-user-management',
  templateUrl: 'user.management.component.html'
})
export class UserManagementComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('User Management');
  }

}
