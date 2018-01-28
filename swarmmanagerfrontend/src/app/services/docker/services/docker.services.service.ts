import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerServicesSummary } from './docker.services.summary';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs/Observable';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import 'rxjs/add/operator/first';
import { DockerService } from './docker.service';

@Injectable()
export class DockerServicesService extends DockerBaseService {

  private dockerServicesUrl = '/services';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getServicesList(noMessage?: boolean): Observable<DockerServicesSummary[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerServicesSummary[]>(this.dockerSwarmUrl + this.dockerServicesUrl)
          .first()
          .subscribe(
            (services: DockerServicesSummary[]) => {
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' services!', services, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' services!');
            });
      });
    });
  }

  getService(name: string, noMessage?: boolean): Observable<DockerService> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerService>(this.dockerSwarmUrl + this.dockerServicesUrl + '/' + name)
          .first()
          .subscribe(
            (service: DockerService) => {
              this.completeWithSuccess(observer, 'Loaded ' + name + ' service!', service, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + name + ' service!');
            });
      });
    });
  }

  removeService(name: string): Observable<void> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.delete(this.dockerSwarmUrl + this.dockerServicesUrl + '/' + name, {
          observe: 'response',
          responseType: 'text'
        }).subscribe(
          (resp: HttpResponse<any>) => {
            this.completeWithSuccess(observer, null, null);
          },
          (err: HttpErrorResponse) => {
            this.completeWithError(err, observer, 'Failed to remove service ' + name + '!');
          }
        );
      });
    });
  }

  createService(dockerService: DockerService): Observable<any> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.post<DockerService>(this.dockerSwarmUrl + this.dockerServicesUrl, dockerService)
          .subscribe(
            (returnedService: DockerService) => {
              this.completeWithSuccess(observer, 'Created ' + returnedService.name + ' service!', returnedService);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to create service!');
            });
      });
    });
  }

  updateService(dockerService: DockerService): Observable<any> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.put<DockerService>(this.dockerSwarmUrl + this.dockerServicesUrl + '/' + dockerService.name, dockerService)
          .subscribe(
            (returnedService: DockerService) => {
              this.completeWithSuccess(observer, 'Updated ' + returnedService.name + ' service!', returnedService);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to update service!');
            });
      });
    });
  }
}


