import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { ActivatedRoute } from '@angular/router';
import { DockerServicesSummary } from '../../services/docker/services/docker.services.summary';
import { DockerServicesService } from '../../services/docker/services/docker.services.service';
import { BrowserService } from '../../services/utils/browser.service';
import { SnackbarService } from '../../services/snackbar/snackbar.service';

@Component({
  selector: 'app-services',
  styleUrls: ['services.view.scss'],
  templateUrl: 'services.view.html'
})
export class ServicesView extends BaseView {

  services: DockerServicesSummary[] = [];
  filter = '';

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private dockerServicesService: DockerServicesService,
              private browserService: BrowserService,
              public snackbarService: SnackbarService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    this.refreshFunction = this.refreshNetworks;
    this.refreshNetworks(true);
  }

  refreshNetworks(noMessage?: boolean): void {
    super.addSubscription(this.dockerServicesService.getServicesList(noMessage).subscribe(
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
