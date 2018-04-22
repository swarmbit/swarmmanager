import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import 'rxjs/add/operator/first';
import {DockerSecret} from './docker.secret';

@Injectable()
export class DockerSecretsService extends DockerBaseService {

  private dockerSecretsUrl = '/secrets';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getSecretsList(noMessage?: boolean): Observable<DockerSecret[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerSecret[]>(this.dockerSwarmUrl + this.dockerSecretsUrl)
          .first()
          .subscribe(
            (configs: DockerSecret[]) => {
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' secrets', configs, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' secrets');
            });
      });
    });
  }

  getSecret(name: string, noMessage?: boolean): Observable<DockerSecret> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerSecret>(this.dockerSwarmUrl + this.dockerSecretsUrl + '/' + name)
          .first()
          .subscribe(
            (config: DockerSecret) => {
              this.completeWithSuccess(observer, 'Loaded ' + name + ' secret', config, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + name + ' secret');
            });
      });
    });
  }

  removeSecret(name: string): Observable<void> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.delete(this.dockerSwarmUrl + this.dockerSecretsUrl + '/' + name, {
          observe: 'response',
          responseType: 'text'
        }).subscribe(
          (resp: HttpResponse<any>) => {
            this.completeWithSuccess(observer, 'Removed ' + name + ' secret', null);
          },
          (err: HttpErrorResponse) => {
            this.completeWithError(err, observer, 'Failed to remove secret ' + name);
          }
        );
      });
    });
  }

  createSecret(dockerSecret: DockerSecret): Observable<any> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.post<DockerSecret>(this.dockerSwarmUrl + this.dockerSecretsUrl, dockerSecret)
          .subscribe(
            (returnedConfig: DockerSecret) => {
              this.completeWithSuccess(observer, 'Created ' + name + ' secret', returnedConfig);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to create secret');
            });
      });
    });
  }
}


