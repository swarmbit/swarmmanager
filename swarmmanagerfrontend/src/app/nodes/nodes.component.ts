
import {Component, OnInit} from '@angular/core';
import {RoutingService} from '../routing/routing.service';

@Component({
  selector: 'app-nodes',
  templateUrl: 'nodes.component.html'
})
export class NodesComponent implements OnInit {

  routingService: RoutingService;

  constructor(routingService: RoutingService) {
    this.routingService = routingService;
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Nodes');
  }

}
