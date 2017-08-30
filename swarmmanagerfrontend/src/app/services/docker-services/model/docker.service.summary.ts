import { Port } from './port';

export class DockerServiceSummary {

  id: string;

  name: string;

  runningReplicas: number;

  replicas: number;

  global: boolean;

  image: string;

  ports: Port[];

}
