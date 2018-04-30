import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { DockerServiceState } from '../../../services/docker/services/docker.service.state';

@Injectable()
export class DockerServiceStateResolver implements Resolve<DockerServiceState> {

  constructor(private dockerServicesService: DockerServicesService,
              private router: Router,
              private browserService: BrowserService,
              private swarmService: DockerSwarmService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
          Observable<DockerServiceState> | Promise<DockerServiceState> | DockerServiceState {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getServiceState(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getServiceState(route, observer);
        });
      }
    });
  }

  private getServiceState(route, observer): void {
    const name = route.params['name'];
    this.dockerServicesService.getServiceState(name).subscribe(
      (state: DockerServiceState) => {
        observer.next(state);
        observer.complete();
      }, (err: any) => {
        if (!this.browserService.cannotGoBack()) {
          this.router.navigate([this.browserService.goBack()]);
        } else {
          this.router.navigate(['services/' + name]);
        }
        observer.complete();
      }
    );
  }
}
