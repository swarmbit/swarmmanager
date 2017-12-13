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
               private snackbarService: SnackbarService) {
    super(swarmsService);
  }

  getNodesList(): Observable<DockerNodeSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNodeSummary[]>(this.dockerSwarmUrl + this.dockerNodesUrl)
          .first()
          .subscribe(
            (nodes: DockerNodeSummary[]) => {
              observer.next(nodes);
              observer.complete();
              this.snackbarService.showSuccess('Loaded ' + this.dockerSwarmName + ' nodes!');
            },
            (err: HttpErrorResponse) => {
              console.log(err);
              if (err.status === 100001) {
                this.snackbarService.showError('Failed to loaded ' + this.dockerSwarmName + ' nodes! ' + err.message);
              } else {
                this.snackbarService.showError('Failed to loaded ' + this.dockerSwarmName + ' nodes!');
              }
            });
      });
    });
  }

}


