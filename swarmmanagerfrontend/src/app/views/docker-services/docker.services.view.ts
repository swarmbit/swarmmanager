
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ServicesService } from '../../services/docker-services/docker.services.service';
import { ServiceSummary } from '../../services/docker-services/model/service.summary';
import { BehaviorSubject, Observable, Subscription } from 'rxjs/Rx';
import { DataSource } from '@angular/cdk';
import { HeaderInfo } from '../../services/header/header.info';
import { HeaderService } from '../../services/header/header.service';
import { SmTableContent } from '../../components/sm-table/sm.table.content';
import { ServiceSummaryRows } from './service.summary.rows';

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
  //sserviceTableContent: SmTableContent;

  constructor(serviceService: ServicesService, headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Services';
    this.headerService.setHeaderInfo(headerInfo);
    this.serviceService = serviceService;
    this.servicesSummary = [];
    this.filter = '';
    /*
    this.serviceTableContent = new SmTableContent();
    let columns = new Map();
    columns.set('Id', 'id');
    columns.set('Name', 'name');
    columns.set('Replicas', 'replicas');
    columns.set('Image', 'image');
    columns.set('Ports', 'ports');
    this.serviceTableContent.columns = columns;*/
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
          // Create table component using angular material table
          // this.serviceTableContent.database.replaceData(new ServiceSummaryRows(this.servicesSummary).rows);
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
