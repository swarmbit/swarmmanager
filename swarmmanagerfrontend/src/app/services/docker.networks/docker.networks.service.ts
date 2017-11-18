import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerNetworkSummary } from './docker.network.summary';
import { DockerSwarmService } from '../docker.swarms/docker.swarms.service';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class DockerNetworksService {

  private dockerNetworksUrl = '/networks';

  constructor (private http: HttpClient, private swarmsService: DockerSwarmService) {
  }

  getNetworksList(): Observable<DockerNetworkSummary[]> {
    return Observable.create(observer => {
      this.swarmsService.getSelectedSwarmId().then(
        id => {
          this.http.get<DockerNetworkSummary[]>(DockerSwarmService.DOCKER_SWARMS_URL + id + this.dockerNetworksUrl)
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
              }
            );
        }
      );
    });
  }

}


