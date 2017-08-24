import { Port } from './port';

export class ServiceSummary {

  id: string;

  name: string;

  runningReplicas: number;

  replicas: number;

  global: boolean;

  image: string;

  ports: Port[];

}
