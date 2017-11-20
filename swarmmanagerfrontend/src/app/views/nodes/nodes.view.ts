
import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { DockerNodeSummary } from '../../services/docker/nodes/docker.node.summary';
import { DockerNodesService } from '../../services/docker/nodes/docker.nodes.service';

@Component({
  selector: 'app-nodes',
  styleUrls: ['nodes.view.scss'],
  templateUrl: 'nodes.view.html'
})
export class NodesView extends BaseView {

  nodes: DockerNodeSummary[] = [];
  filter = '';

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private nodesService: DockerNodesService
  ) {
    super(headerService, 'Nodes', swarmService, userService);
    this.refreshFunction = this.refreshNodes;
    this.refreshNodes();
  }

  refreshNodes(): void {
    this.nodesService.getNodesList().subscribe(
      (nodes: DockerNodeSummary[]) => {
        this.nodes = nodes;
        this.nodes.sort((value1, value2) => {
          if (value1.hostname < value2.hostname ) {
            return -1;
          }
          if (value1.hostname > value2.hostname ) {
            return 1;
          }
          return 0;
        });
      }
    );
  }

  getNodes(): DockerNodeSummary[] {
    return ViewUtils.getFilteredArray(this.filter, this.nodes, 'hostname');
  }
}
