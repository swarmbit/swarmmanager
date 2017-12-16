import { Component, OnDestroy } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerNetworksService } from '../../services/docker/networks/docker.networks.service';
import { DockerNetworkSummary } from '../../services/docker/networks/docker.network.summary';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-networks',
  styleUrls: ['networks.view.scss'],
  templateUrl: 'networks.view.html'
})
export class NetworksView extends BaseView {

  networks: DockerNetworkSummary[] = [];
  filter = '';

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private networksService: DockerNetworksService
              ) {
    super(headerService, route, swarmService, userService);
    this.refreshFunction = this.refreshNetworks;
    this.refreshNetworks();
  }

  refreshNetworks(): void {
    super.addSubscription(this.networksService.getNetworksList().subscribe(
      (networks: DockerNetworkSummary[]) => {
        this.networks = networks;
        this.networks.sort(ViewUtils.sortByName);
      }
    ));
  }

  getNetworks(): DockerNetworkSummary[] {
    return ViewUtils.getFilteredArray(this.filter, this.networks, 'name');
  }

}
