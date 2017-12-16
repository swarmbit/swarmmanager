import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs/Observable';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import { DockerNodeSummary } from './docker.node.summary';
import 'rxjs/add/operator/first';


@Injectable()
export class DockerNodesService extends DockerBaseService {

  private dockerNodesUrl = '/nodes';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getNodesList(): Observable<DockerNodeSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNodeSummary[]>(this.dockerSwarmUrl + this.dockerNodesUrl)
          .first()
          .subscribe(
            (nodes: DockerNodeSummary[]) => {
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' nodes!', nodes);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' nodes!');
            });
      });
    });
  }

}


