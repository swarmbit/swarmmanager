export class DockerServiceMount {
  type: string;
  source: string;
  destination: string;
  readOnly: boolean;
  consistency: string;
  bindOptions: BindMountOptions;
  volumeOptions: DockerVolumeMountOptions;
  tmpfsOptions: DockerTmpfsMountOptions;
}

export class DockerTmpfsMountOptions {
  sizeBytes: number;
  mode: number;
}


export class DockerVolumeMountOptions {
  noCopy: boolean;
  labels: any;
  driver: string;
  driverOptions: any;
}

export class BindMountOptions {
  propagation: string;
}
