import { DockerServicePort } from './docker.service.port';
import { DockerServiceMount } from './docker.service.mount';

export class DockerService {
  id: string;
  createdAt: string;
  updatedAt: string;
  image: string;
  global: boolean;
  name: string;
  replicas: number;
  ports: DockerServicePort[];
  configs: string[];
  secrets: string[];
  labels: any;
  containerLabels: any;
  constraints: any;
  placementPreferences: any;
  readOnly: boolean;
  entrypoint: string;
  args: string[];
  groups: string[];
  logDriver: string[];
  logOptions: any;
  registryName: any;
  user: any;
  workDir: any;
  stopGracePeriod: any;
  stopSignal: any;

  mounts: DockerServiceMount;

  forceUpdate: boolean;

  /**
   * Network
   */
  endpointMode: string;
  networks: string[];
  hostname: string;
  hosts: string[];

  /**
   * Dns
   */
  dnsServers: string[];
  dnsOptions: string[];
  dansSearches: string[];

  /**
   * Health Check
   */
  healthCmd: string;
  healthRetries: number;
  healthStartPeriod: string;
  healthInterval: string;
  healthTimeout: string;
  noHealthCheck: boolean;

  /**
   * Reserve
   */
  reserveCpu: number;
  reserveMemory: number;

  /**
   * Limits
   */
  limitCpu: number;
  limitMemory: number;

  /**
   * Restarts
   */
  restartCondition: string;
  restartDelay: string;
  restartMaxAttempts: number;
  restartWindow: string;

  /**
   * Updates
   */
  updateDelay: string;
  updateFailureAction: string;
  updateFailureRatio: number;
  updateMonitor: string;
  updateOrder: string;
  updateParallelism: number;

  /**
   * Rollbacks
   */
  rollbackDelay: string;
  rollbackFailureAction: string;
  rollbackMaxFailureRatio: number;
  rollbackMonitor: string;
  rollbackOrder: string;
  rollbackParallelism: number;
}
