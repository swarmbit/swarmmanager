import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { DockerService } from '../../../services/docker/services/docker.service';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';

@Injectable()
export class DockerServiceResolver implements Resolve<DockerService> {

  constructor(private dockerServicesService: DockerServicesService,
              private router: Router,
              private browserService: BrowserService,
              private swarmService: DockerSwarmService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerService> | Promise<DockerService> | DockerService {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getService(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getService(route, observer);
        });
      }
    });
  }

  private getService(route, observer): void {
    this.dockerServicesService.getService(route.params['name'], true).subscribe(
      (dockerService: DockerService) => {
        observer.next(dockerService);
        observer.complete();
      }, (err: any) => {
        const backUrl = this.browserService.getBackUrl();
        if (backUrl) {
          this.router.navigate([backUrl]);
        } else {
          this.router.navigate(['services']);
        }
        observer.complete();
      }
    );
  }
}
