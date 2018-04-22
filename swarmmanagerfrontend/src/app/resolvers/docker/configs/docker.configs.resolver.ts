import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { DockerConfig } from '../../../services/docker/configs/docker.config';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';
import { DockerConfigsService } from '../../../services/docker/configs/docker.configs.service';


@Injectable()
export class DockerConfigsResolver implements Resolve<DockerConfig> {

  constructor(private dockerConfigService: DockerConfigsService,
              private router: Router,
              private browserService: BrowserService,
              private swarmService: DockerSwarmService,
              private snackbar: SnackbarService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerConfig> | Promise<DockerConfig> | DockerConfig {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getConfig(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getConfig(route, observer);
        });
      }
    });
  }

  getConfig(route, observer): void {
    if (!this.swarmService.equalsOrGreaterThenVersion30()) {
      this.router.navigate(['']);
      this.snackbar.showError('Selected swarm does not support docker configs')
    } else {
      this.dockerConfigService.getConfig(route.params['name'], true).subscribe(
        (dockerConfig: DockerConfig) => {
          observer.next(dockerConfig);
          observer.complete();
        }, (err: any) => {
          const backUrl = this.browserService.getCurrentUrl();
          if (backUrl) {
            this.router.navigate([backUrl]);
          } else {
            this.router.navigate(['configs']);
          }
          observer.complete();
        }
      );
    }
  }
}
