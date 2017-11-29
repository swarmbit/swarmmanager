import { Component } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BaseView } from '../../base.view';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-network',
  styleUrls: ['create.network.view.scss'],
  templateUrl: 'create.network.view.html'
})
export class CreateNetworkView extends BaseView {

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private networksService: DockerNetworksService
  ) {
    super(headerService, 'Create Network', swarmService, userService);
    super.enableBackArrow('/networks');
  }

  createNetwork(form: NgForm): void {
    console.log(form);
  }

}

