import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { DockerServiceSummary } from './model/docker.service.summary';
import { DockerService } from './model/docker.service';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class DockerServicesService {

  private serviceLs = '/api/service/ls';

  private serviceCreate = '/api/service/create';

  private service = '/api/service';

  constructor (private http: HttpClient) {}

  executeServiceLs(): Observable<DockerServiceSummary[]> {
    return this.http.get(this.serviceLs);
  }

  createService(service: DockerService): Observable<DockerServiceSummary[]> {
    return this.http.post(this.serviceCreate, service);
  }

  getService(id: number): Observable<DockerService> {
    return this.http.get(this.service + '/' + id);
  }

  updateService(id: number, service: DockerService): Observable<DockerService> {
    return this.http.put(this.service + '/' + id, service);
  }

  deleteService(id: number): Observable<VoidFunction> {
    return this.http.delete(this.service + '/' + id);
  }

}
