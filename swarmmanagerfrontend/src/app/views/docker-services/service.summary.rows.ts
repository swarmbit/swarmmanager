import { DockerServiceSummary } from '../../services/docker-services/model/docker.service.summary';
import { CleanServiceImagePipe } from './pipes/clean.service.image.pipe';

export class ServiceSummaryRows {

  rows: ServiceSummaryRow[];

  constructor(servicesSummary: DockerServiceSummary[]) {
    this.rows = [];
    servicesSummary.forEach( serviceSummary => {
      this.rows.push(new ServiceSummaryRow(serviceSummary))
    })
  }

}

export class ServiceSummaryRow {

  id: string;

  name: string;

  replicas: string;

  image: string;

  ports: string;

  constructor(serviceSummary: DockerServiceSummary) {
    this.id = serviceSummary.id;
    this.name = serviceSummary.name;
    this.replicas = serviceSummary.replicas + '/' + serviceSummary.runningReplicas;
    let cleanImagePipe = new CleanServiceImagePipe();
    this.image = cleanImagePipe.transform(serviceSummary.image);
    if (serviceSummary.ports) {
      this.ports = '';
      for (let i = 0; i < serviceSummary.ports.length; i++) {
        let port = serviceSummary.ports[i];
        this.ports = port.protocol + ': ' + port.published + ' -> ' + port.target;
      }
    }
  }

}
