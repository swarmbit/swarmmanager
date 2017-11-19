import { DockerSwarm } from './docker.swarms/docker.swarm';
import { DockerSwarmService } from './docker.swarms/docker.swarms.service';

export class DockerBaseService {

  public dockerSwarmUrl: string;

  public afterDockerSwarmSelected: Promise<void>;

  constructor(private swarmsService: DockerSwarmService) {
    let resolveAfterDockerSwarmSelected;
    this.afterDockerSwarmSelected = new Promise((resolve) => {
      resolveAfterDockerSwarmSelected = resolve;
    });
    this.swarmsService.getSelectedSwarm().subscribe(
      (dockerSwarm: DockerSwarm) => {
        if (dockerSwarm.id) {
          this.dockerSwarmUrl = DockerSwarmService.DOCKER_SWARMS_URL + dockerSwarm.id;
          resolveAfterDockerSwarmSelected();
        }
      });
  }

}
