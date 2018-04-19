import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarmService } from '../swarms/docker.swarms.service';
import { Observable } from 'rxjs';
import { DockerBaseService } from '../docker.base.service';
import { SnackbarService } from '../../snackbar/snackbar.service';
import 'rxjs/add/operator/first';
import { DockerConfig } from './docker.config';

@Injectable()
export class DockerConfigService extends DockerBaseService {

  private dockerConfigsUrl = '/configs.secrets';

  constructor (private http: HttpClient,
               private swarmsService: DockerSwarmService,
               snackbarService: SnackbarService) {
    super(swarmsService, snackbarService);
  }

  getConfigsList(noMessage?: boolean): Observable<DockerConfig[]> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerConfig[]>(this.dockerSwarmUrl + this.dockerConfigsUrl)
          .first()
          .subscribe(
            (configs: DockerConfig[]) => {
              this.completeWithSuccess(observer, 'Loaded ' + this.dockerSwarmName + ' configs', configs, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + this.dockerSwarmName + ' configs');
            });
      });
    });
  }

  getConfig(name: string, noMessage?: boolean): Observable<DockerConfig> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.get<DockerConfig>(this.dockerSwarmUrl + this.dockerConfigsUrl + '/' + name)
          .first()
          .subscribe(
            (config: DockerConfig) => {
              this.completeWithSuccess(observer, 'Loaded ' + name + ' config', config, noMessage);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to load ' + name + ' config');
            });
      });
    });
  }

  removeConfig(name: string): Observable<void> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.delete(this.dockerSwarmUrl + this.dockerConfigsUrl + '/' + name, {
          observe: 'response',
          responseType: 'text'
        }).subscribe(
          (resp: HttpResponse<any>) => {
            this.completeWithSuccess(observer, 'Removed ' + name + ' config', null);
          },
          (err: HttpErrorResponse) => {
            this.completeWithError(err, observer, 'Failed to remove config ' + name);
          }
        );
      });
    });
  }

  createConfig(dockerConfig: DockerConfig): Observable<any> {
    return Observable.create(observer => {
      this.afterDockerSwarmSelected.then(() => {
        this.http.post<DockerConfig>(this.dockerSwarmUrl + this.dockerConfigsUrl, dockerConfig)
          .subscribe(
            (returnedConfig: DockerConfig) => {
              this.completeWithSuccess(observer, 'Created ' + name + ' config', returnedConfig);
            },
            (err: HttpErrorResponse) => {
              this.completeWithError(err, observer, 'Failed to create config');
            });
      });
    });
  }
}


