
import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerNetworksService } from '../../services/docker.networks/docker.networks.service';
import { DockerNetworkSummary } from '../../services/docker.networks/docker.network.summary';

@Component({
  selector: 'app-networks',
  styleUrls: ['networks.view.scss'],
  templateUrl: 'networks.view.html'
})
export class NetworksView extends BaseView {

  networks: DockerNetworkSummary[] = [];
  filter: string = '';

  constructor(headerService: HeaderService, private networksService: DockerNetworksService) {
    super(headerService, 'Networks');
    this.networksService.getNetworksList().subscribe(
      (networks: DockerNetworkSummary[]) => {
        this.networks = networks;
      }
    );
  }

}
