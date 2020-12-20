import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import { DockerNodeSummary } from './docker.node.summary';
import { first } from 'rxjs/operators';;

@Injectable()
export class DockerNodesService extends DockerBaseService {

  private dockerNodesUrl = '/nodes';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getNodesList(noMessage?: boolean): Observable<DockerNodeSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNodeSummary[]>(this.dockerSwarmUrl + this.dockerNodesUrl)
          .pipe(first())
          .subscribe(
            (nodes: DockerNodeSummary[]) => {
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' nodes', nodes, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' nodes');
            });
      });
    });
  }

}


