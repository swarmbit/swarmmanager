import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { DockerService } from '../../../services/docker/services/docker.service';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';

@Injectable()
export class DockerServiceResolver implements Resolve<DockerService> {

  constructor(private dockerServicesService: DockerServicesService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerService> | Promise<DockerService> | DockerService {
    return Observable.create( observer => {
      this.dockerServicesService.getService(route.params['name'], true).subscribe(
        (dockerService: DockerService) => {
          observer.next(dockerService);
          observer.complete();
        }, (err: any) => {
          this.router.navigate(['/services']);
          observer.complete();
        }
      );;
    });
  }
}
