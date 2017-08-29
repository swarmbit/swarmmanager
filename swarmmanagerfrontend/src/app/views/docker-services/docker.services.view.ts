
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ServicesService } from '../../services/docker-services/docker.services.service';
import { ServiceSummary } from '../../services/docker-services/model/service.summary';
import { Observable, Subscription } from 'rxjs/Rx';
import { HeaderInfo } from '../../services/header/header.info';
import { HeaderService } from '../../services/header/header.service';

@Component({
  selector: 'app-services',
  styleUrls: ['docker.services.view.scss'],
  templateUrl: 'docker.services.view.html'
})
export class DockerServicesView implements OnInit, OnDestroy {

  headerService: HeaderService;
  serviceService: ServicesService;
  errorMessage: string;
  servicesSummary: ServiceSummary[];
  subscription: Subscription;
  filter: string;

  constructor(serviceService: ServicesService, headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Services';
    this.headerService.setHeaderInfo(headerInfo);
    this.serviceService = serviceService;
    this.servicesSummary = [];
    this.filter = '';
  }

  ngOnInit(): void {
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
