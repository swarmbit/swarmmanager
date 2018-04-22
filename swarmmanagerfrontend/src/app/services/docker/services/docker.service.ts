import { DockerServicePort } from './docker.service.port';
import { DockerServiceMount } from './docker.service.mount';
import { DockerServiceSecretAndConfig } from './docker.service.secrect.and.config';

export class DockerService {
  id: string;
  createdAt: string;
  updatedAt: string;
  image: string;
  global: boolean;
  name: string;
  replicas = 1;
  ports: DockerServicePort[];
  env: string[];
  configs: DockerServiceSecretAndConfig[];
  secrets: DockerServiceSecretAndConfig[];
  labels: any;
  containerLabels: any;
  constraints: any;
  placementPreferences: any;

  workDir: string;
  entrypoint: string;
  args: string[];
  user: string;
  groups: string[];
  stopGracePeriod: number;
  stopSignal: string;
  readOnly: boolean;

  logDriver: string;
  logOptions: any;

  mounts: DockerServiceMount[];

  /**
   * Dns
   */
  dnsServers: string[];
  dnsOptions: string[];
  dnsSearches: string[];

  dockerHubRegistry: boolean;
  registryName: string;
  registryUsername: string;
  registryPassword: string;

  forceUpdate: boolean;

  /**
   * Network
   */
  endpointMode: string;
  networks: string[];
  hostname: string;
  hosts: string[];

  /**
   * Health Check
   */
  healthCmd: string;
  healthRetries: number;
  healthStartPeriod: number;
  healthInterval: number;
  healthTimeout: number;
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
  restartDelay: number;
  restartMaxAttempts: number;
  restartWindow: number;

  /**
   * Updates
   */
  updateDelay: number;
  updateFailureAction: string;
  updateMaxFailureRatio: number;
  updateMonitor: number;
  updateOrder: string;
  updateParallelism: number;

  /**
   * Rollbacks
   */
  rollbackDelay: number;
  rollbackFailureAction: string;
  rollbackMaxFailureRatio: number;
  rollbackMonitor: number;
  rollbackOrder: string;
  rollbackParallelism: number;

  rollback: boolean;
}
