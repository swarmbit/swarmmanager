import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { DockerServiceLogs } from '../../../services/docker/services/docker.service.logs';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { BrowserService } from '../../../services/utils/browser.service';

@Injectable()
export class DockerServiceLogsResolver implements Resolve<DockerServiceLogs> {

  constructor(private dockerServicesService: DockerServicesService,
              private swarmService: DockerSwarmService,
              private router: Router,
              private browserService: BrowserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerServiceLogs>
    | Promise<DockerServiceLogs> | DockerServiceLogs {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getLogs(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getLogs(route, observer);
        });
      }
    });
  }

  getLogs(route, observer): void {
    this.swarmService.getSelectedSwarm().toPromise().then(() => {
      if (this.swarmService.equalsOrGreaterThenVersion29()) {
        this.dockerServicesService.getServiceLogs(route.params[ 'name' ]).subscribe(
          (dockerServiceLogs: DockerServiceLogs) => {
            observer.next(dockerServiceLogs);
            observer.complete();
          }, () => {
            observer.complete();
            const backUrl = this.browserService.getBackUrl();
            if (backUrl) {
              this.router.navigate([backUrl]);
            } else {
              this.router.navigate(['services']);
            }
          }
        );
      } else {
        observer.complete();
        const backUrl = this.browserService.getBackUrl();
        if (backUrl) {
          this.router.navigate([backUrl]);
        } else {
          this.router.navigate(['services']);
        }
      }
    });
  }
}
