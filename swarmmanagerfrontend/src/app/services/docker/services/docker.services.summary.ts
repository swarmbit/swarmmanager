import { DockerServicePort } from './docker.service.port';
export class DockerServicesSummary {
  id: string;
  name: string;
  runningReplicas: number;
  replicas: number;
  global: boolean;
  image: string;
  ports: DockerServicePort[];
}
