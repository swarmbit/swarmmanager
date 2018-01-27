import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerNetworkSummary } from './docker.network.summary';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs/Observable';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import 'rxjs/add/operator/first';
import { DockerNetwork } from './docker.network';

@Injectable()
export class DockerNetworksService extends DockerBaseService {

  private dockerNetworksUrl = '/networks';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getNetworksList(noMessage?: boolean): Observable<DockerNetworkSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNetworkSummary[]>(this.dockerSwarmUrl + this.dockerNetworksUrl)
          .first()
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
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' networks!', networksReturn, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' networks!');
            });
      });
    });
  }

  getNetwork(name: string, noMessage?: boolean): Observable<DockerNetwork> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerNetwork>(this.dockerSwarmUrl + this.dockerNetworksUrl + '/' + name)
          .first()
          .subscribe(
            (network: DockerNetwork) => {
              this.completeWithSuccess(observer, 'Loaded ' + name + ' network!', network, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + name + ' network!');
            });
      });
    });
  }

  removeNetwork(name: string): Observable<void> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.delete(this.dockerSwarmUrl + this.dockerNetworksUrl + '/' + name, {
          observe: 'response',
          responseType: 'text'
        }).subscribe(
          (resp: HttpResponse<any>) => {
            this.completeWithSuccess(observer, 'Removed ' + name + ' network!', null);
          },
          (err: HttpErrorResponse) => {
            this.completeWithError(err, observer, 'Failed to remove network ' + name + '!');
          }
        );
      });
    });
  }

  createNetwork(dockerNetwork: DockerNetwork): Observable<any> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.post<DockerNetwork>(this.dockerSwarmUrl + this.dockerNetworksUrl, dockerNetwork)
          .subscribe(
            (returnedNetwork: DockerNetwork) => {
              this.completeWithSuccess(observer, 'Created ' + name + ' network!', returnedNetwork);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to create network!');
            });
      });
    });
  }
}


