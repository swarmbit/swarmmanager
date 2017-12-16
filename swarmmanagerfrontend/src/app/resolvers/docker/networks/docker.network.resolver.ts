import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';

@Injectable()
export class DockerNetworkResolver implements Resolve<DockerNetwork> {

  constructor(private dockerNetworksService: DockerNetworksService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerNetwork> | Promise<DockerNetwork> | DockerNetwork {
    return this.dockerNetworksService.getNetwork(route.params['name']);
  }
}
