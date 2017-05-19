
import { Component, OnInit, OnDestroy } from '@angular/core';
import { RoutingService } from '../routing/routing.service';
import { ServicesService } from './services-service/services.service';
import { ServiceSummary } from './model/service.summary';
import { Observable, Subscription } from 'rxjs/Rx';

@Component({
  selector: 'app-services',
  styleUrls: ['services.component.scss'],
  templateUrl: 'services.component.html'
})
export class ServicesComponent implements OnInit, OnDestroy {

  routingService: RoutingService;
  serviceService: ServicesService;
  errorMessage: string;
  servicesSummary: ServiceSummary[];
  subscription: Subscription;
  filter: string;

  constructor(serviceService: ServicesService, routingService: RoutingService) {
    this.routingService = routingService;
    this.serviceService = serviceService;
    this.servicesSummary = [];
    this.filter = '';
  }

  ngOnInit(): void {
    this.routingService.setHeaderName('Services');
    this.executeServiceLs();
    this.subscription = Observable
      .interval(5000)
      .subscribe(() => this.executeServiceLs());
  }

  executeServiceLs() {
    this.serviceService.executeServiceLs()
      .subscribe(
        servicesSummary => {
          this.servicesSummary = servicesSummary;
        },
        error =>  this.errorMessage = <any>error);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  clearFilter() {
    this.filter = '';
  }

  servicesSummaryFiltered(): ServiceSummary[] {
    const servicesSummaryFiltered = [];
    for (const serviceSummary of this.servicesSummary) {
      if (serviceSummary.name.indexOf(this.filter.toLowerCase()) > -1 ||
            serviceSummary.id.indexOf(this.filter.toLowerCase()) > -1) {
        servicesSummaryFiltered.push(serviceSummary);
      }
    }
    return servicesSummaryFiltered;
  }

}
