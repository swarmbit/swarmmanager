import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';

@Injectable()
export class DockerNetworkResolver implements Resolve<DockerNetwork> {

  constructor(private dockerNetworksService: DockerNetworksService,
              private router: Router,
              private browserService: BrowserService,
              private swarmService: DockerSwarmService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerNetwork> | Promise<DockerNetwork> | DockerNetwork {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getNetwork(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getNetwork(route, observer);
        });
      }
    });
  }

  getNetwork(route, observer): void {
    this.dockerNetworksService.getNetwork(route.params['name'], true).subscribe(
      (dockerNetwork: DockerNetwork) => {
        observer.next(dockerNetwork);
        observer.complete();
      }, (err: any) => {
        const backUrl = this.browserService.getBackUrl();
        if (backUrl) {
          this.router.navigate([backUrl]);
        } else {
          this.router.navigate(['networks']);
        }
        observer.complete();
      }
    );
  }
}
