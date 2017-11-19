
import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerNetworksService } from '../../services/docker/networks/docker.networks.service';
import { DockerNetworkSummary } from '../../services/docker/networks/docker.network.summary';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';

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
              private networksService: DockerNetworksService
              ) {
    super(headerService, 'Networks', swarmService, userService);
    this.refreshFunction = this.refreshNetworks;
    this.refreshNetworks();
  }

  refreshNetworks(): void {
    this.networksService.getNetworksList().subscribe(
      (networks: DockerNetworkSummary[]) => {
        this.networks = networks;
        this.networks.sort(ViewUtils.sortByName);
      }
    );
  }

  getNetworks(): DockerNetworkSummary[] {
    return ViewUtils.getFilteredArray(this.filter, this.networks, 'name');
  }

}
