import { DockerSwarm } from './swarms/docker.swarm';
import { DockerSwarmService } from './swarms/docker.swarms.service';
import { Observer } from 'rxjs/Observer';

import { SnackbarService } from '../snackbar/snackbar.service';

export class DockerBaseService {

  public dockerSwarmUrl: string;
  public dockerSwarmName: string;

  public afterDockerSwarmSelected: Promise<void>;

  constructor(swarmsService: DockerSwarmService, private snackbarService: SnackbarService) {
    let resolveAfterDockerSwarmSelected;
    this.afterDockerSwarmSelected = new Promise((resolve) => {
      resolveAfterDockerSwarmSelected = resolve;
    });
    swarmsService.onSwarmChange().subscribe(
      (dockerSwarm: DockerSwarm) => {
        if (dockerSwarm.id) {
          this.dockerSwarmUrl = DockerSwarmService.DOCKER_SWARMS_URL + dockerSwarm.id;
          this.dockerSwarmName = dockerSwarm.name;
          resolveAfterDockerSwarmSelected();
        }
      });
  }

  completeWithSuccess(observer: Observer<any>, message: string, returnObject?: any, noMessage?: boolean) {
    if (message && !noMessage) {
      this.snackbarService.showSuccess(message);
    }
    observer.next(returnObject);
    observer.complete();
  }

  completeWithError(err: any, observer: Observer<any>, message: string) {
    let showedMessage = false;
    if (err && err.error && err.error.errors) {
      const error = err.error.errors[ 0 ];
      if (error.code === 'docker') {
        this.snackbarService.showError(message + '. ' + error.message);
        showedMessage = true;
      }
    }
    if (!showedMessage) {
      this.snackbarService.showError(message);
    }
    observer.error(err);
    observer.complete();
  }

}
