import { Injectable } from '@angular/core';
import { DockerServiceSummary } from './model/docker.service.summary';
import { DockerService } from './model/docker.service';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class DockerServicesService {

  private serviceLs = '/api/service/ls';

  private serviceCreate = '/api/service/create';

  private service = '/api/service';

  constructor (private http: HttpClient) {}

  executeServiceLs(): Promise<DockerServiceSummary[]> {
    return this.http.get(this.serviceLs).toPromise();
  }

  createService(service: DockerService): Promise<DockerServiceSummary[]> {
    return this.http.post(this.serviceCreate, service).toPromise();
  }

  getService(id: string): Promise<DockerService> {
    return this.http.get(this.service + '/' + id).toPromise();
  }

  updateService(id: string, service: DockerService): Promise<DockerService> {
    return this.http.put(this.service + '/' + id, service).toPromise();
  }

  deleteService(id: string): Promise<VoidFunction> {
    return this.http.delete(this.service + '/' + id).toPromise();
  }

}
