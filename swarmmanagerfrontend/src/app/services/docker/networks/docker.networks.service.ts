import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerNetworkSummary } from './docker.network.summary';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs/Observable';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';

@Injectable()
export class DockerNetworksService extends DockerBaseService {

  private dockerNetworksUrl = '/networks';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               private snackbarService: SnackbarService) {
    super(swarmsService);
  }

  getNetworksList(): Observable<DockerNetworkSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNetworkSummary[]>(this.dockerSwarmUrl + this.dockerNetworksUrl)
          .subscribe(
            (networks: DockerNetworkSummary[]) => {
              const networksReturn: DockerNetworkSummary[] = [];
              if (networks) {
                for (const network of networks) {
                  if (network.scope == 'swarm') {
                    networksReturn.push(network);
                  }
                }
              }
              observer.next(networksReturn);
              observer.complete();
              this.snackbarService.showSuccess('Loaded ' + this.dockerSwarmName + ' networks!');
            },
            (err: HttpErrorResponse) => {
              this.snackbarService.showError('Failed to loaded ' + this.dockerSwarmName + ' networks! ' + err.message);
            });
      });
    });
  }

}


