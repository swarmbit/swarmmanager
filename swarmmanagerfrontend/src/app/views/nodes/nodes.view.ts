
import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-nodes',
  templateUrl: 'nodes.view.html'
})
export class NodesView extends BaseView {

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService) {
    super(headerService, 'Nodes', swarmService, userService);
  }

}
