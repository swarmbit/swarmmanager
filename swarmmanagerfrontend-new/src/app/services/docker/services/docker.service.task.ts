import { DockerServicePort } from './docker.service.port';
export class DockerServiceTask {
  id: string;
  replica: number;
  serviceId: string;
  serviceName: string;
  image: string;
  nodeId: string;
  nodeHostname: string;
  desiredState: string;
  state: string;
  lastStateChange: number;
  lastStateDate: number;
  errorMessage: number;
  ports: DockerServicePort[];
}
