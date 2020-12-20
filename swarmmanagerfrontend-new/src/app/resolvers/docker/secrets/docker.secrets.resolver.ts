import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { DockerSecretsService } from '../../../services/docker/secrets/docker.secrets.service';
import { DockerSecret } from '../../../services/docker/secrets/docker.secret';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';

@Injectable()
export class DockerSecretsResolver implements Resolve<DockerSecret> {

  constructor(private dockerSecretsService: DockerSecretsService,
              private router: Router,
              private browserService: BrowserService,
              private swarmService: DockerSwarmService,
              private snackbar: SnackbarService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerSecret> | Promise<DockerSecret> | DockerSecret {
    return Observable.create( observer => {
      if (this.swarmService.isSwarmSelected()) {
        this.getSecret(route, observer);
      } else {
        this.swarmService.getSwarms().subscribe(() => {
          this.getSecret(route, observer);
        });
      }
    });
  }

  getSecret(route, observer): void {
    if (!this.swarmService.equalsOrGreaterThenVersion25()) {
      this.router.navigate(['']);
      this.snackbar.showError('Selected swarm does not support docker secrets')
    } else {
      this.dockerSecretsService.getSecret(route.params[ 'name' ], true).subscribe(
        (dockerSecret: DockerSecret) => {
          observer.next(dockerSecret);
          observer.complete();
        }, (err: any) => {
          const backUrl = this.browserService.getCurrentUrl();
          if (backUrl) {
            this.router.navigate([ backUrl ]);
          } else {
            this.router.navigate([ 'secrets' ]);
          }
          observer.complete();
        }
      );
    }
  }
}
