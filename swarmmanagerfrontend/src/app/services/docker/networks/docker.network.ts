import { DockerIpamConfig } from './docker.ipam.config';
export class DockerNetwork {
  name: string;
  driver: string;
  ipamDriver: string;
  ipamConfigs: DockerIpamConfig[];
  ipamOptions: any;
  labels: any;
  options: any;
  ipv6: boolean;
  internal: boolean;
  attachable: boolean;
}
