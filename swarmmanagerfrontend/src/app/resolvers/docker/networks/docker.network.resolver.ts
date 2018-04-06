import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';

@Injectable()
export class DockerNetworkResolver implements Resolve<DockerNetwork> {

  constructor(private dockerNetworksService: DockerNetworksService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerNetwork> | Promise<DockerNetwork> | DockerNetwork {
    return Observable.create( observer => {
      this.dockerNetworksService.getNetwork(route.params['name'], true).subscribe(
        (dockerNetwork: DockerNetwork) => {
          observer.next(dockerNetwork);
          observer.complete();
        }, (err: any) => {
          this.router.navigate(['/networks']);
          observer.complete();
        }
      );
    });
  }
}
