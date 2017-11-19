import { DockerSwarm } from './swarms/docker.swarm';
import { DockerSwarmService } from './swarms/docker.swarms.service';

export class DockerBaseService {

  public dockerSwarmUrl: string;
  public dockerSwarmName: string;

  public afterDockerSwarmSelected: Promise<void>;

  constructor(swarmsService: DockerSwarmService) {
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

}
