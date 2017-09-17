import { Port } from './port';
export class DockerService {
  name: string;
  image: string;
  global: boolean;
  replicas: number;
  ports: Port[];
}
