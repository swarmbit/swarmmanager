import { Injectable } from '@angular/core';
import { DockerServiceSummary } from './model/docker.service.summary';
import { DockerService } from './model/docker.service';
import { HttpClient } from '@angular/common/http';
import { Logs } from './model/logs';

@Injectable()
export class DockerServicesService {

  private services = '/api/swarms/1/services';

  constructor (private http: HttpClient) {}

  executeServiceLs(): Promise<DockerServiceSummary[]> {
    return this.http.get(this.services).toPromise();
  }

  createService(service: DockerService): Promise<DockerServiceSummary[]> {
    return this.http.post(this.services, service).toPromise();
  }

  getService(id: string): Promise<DockerService> {
    return this.http.get(this.services + '/' + id).toPromise();
  }

  updateService(id: string, service: DockerService): Promise<DockerService> {
    return this.http.put(this.services + '/' + id, service).toPromise();
  }

  getServiceLogs(id: string): Promise<Logs> {
    return this.http.get(this.services + '/' + id + '/logs').toPromise();
  }

  deleteService(id: string): Promise<VoidFunction> {
    return this.http.delete(this.services + '/' + id).toPromise();
  }

}
