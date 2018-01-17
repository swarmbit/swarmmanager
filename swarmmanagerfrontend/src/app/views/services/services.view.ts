import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { ActivatedRoute } from '@angular/router';
import { DockerServicesSummary } from '../../services/docker/services/docker.services.summary';
import { DockerServicesService } from '../../services/docker/services/docker.services.service';

@Component({
  selector: 'app-networks',
  styleUrls: ['services.view.scss'],
  templateUrl: 'services.view.html'
})
export class ServicesView extends BaseView {

  services: DockerServicesSummary[] = [];
  filter = '';

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private dockerServicesService: DockerServicesService
              ) {
    super(headerService, route, swarmService, userService);
    this.refreshFunction = this.refreshNetworks;
    this.refreshNetworks();
  }

  refreshNetworks(): void {
    super.addSubscription(this.dockerServicesService.getServicesList().subscribe(
      (services: DockerServicesSummary[]) => {
        this.services = services;
        this.services.sort(ViewUtils.sortByName);
      }
    ));
  }

  getServices(): DockerServicesSummary[] {
    return ViewUtils.getFilteredArray(this.filter, this.services, 'name');
  }

}
