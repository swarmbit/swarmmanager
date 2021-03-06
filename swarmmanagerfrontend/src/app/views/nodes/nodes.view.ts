
import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { DockerNodeSummary } from '../../services/docker/nodes/docker.node.summary';
import { DockerNodesService } from '../../services/docker/nodes/docker.nodes.service';
import { ActivatedRoute } from '@angular/router';
import { BrowserService } from '../../services/utils/browser.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-nodes',
  styleUrls: ['nodes.view.scss'],
  templateUrl: 'nodes.view.html'
})
export class NodesView extends BaseView {

  nodes: DockerNodeSummary[] = [];
  filter = '';
  refreshSub: Subscription;

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private nodesService: DockerNodesService,
              private route: ActivatedRoute,
              private browserService: BrowserService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    this.refreshFunction = this.refreshNodes;
    this.refreshNodes(true);
  }

  refreshNodes(noMessage?: boolean): void {
    if (this.refreshSub) {
      this.refreshSub.unsubscribe();
      this.refreshSub = null;
    }
    this.refreshSub = this.nodesService.getNodesList(noMessage).subscribe(
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
