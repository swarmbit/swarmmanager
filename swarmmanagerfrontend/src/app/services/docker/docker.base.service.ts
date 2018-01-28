import { DockerSwarm } from './swarms/docker.swarm';
import { DockerSwarmService } from './swarms/docker.swarms.service';
import { Observer } from 'rxjs';
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
    swarmsService.getSelectedSwarm().subscribe(
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
    console.log(err);
    if (err.status == 417) {
      this.snackbarService.showError(message + ' ' + err.error);
    } else {
      this.snackbarService.showError(message);
    }
    observer.error(err);
    observer.complete();
  }

}
