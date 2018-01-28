import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { DockerService } from '../../../services/docker/services/docker.service';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';

@Injectable()
export class DockerServiceResolver implements Resolve<DockerService> {

  constructor(private dockerServicesService: DockerServicesService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DockerService> | Promise<DockerService> | DockerService {
    return this.dockerServicesService.getService(route.params['name'], true);
  }
}
