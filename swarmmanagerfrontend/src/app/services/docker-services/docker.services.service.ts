import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ServiceSummary } from './model/service.summary';
import { Service } from './model/service';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ServicesService {

  private serviceLs = '/api/service/ls';

  private serviceCreate = '/api/service/create';

  private serviceInspect = '/api/service';

  constructor (private http: HttpClient) {}

  executeServiceLs(): Observable<ServiceSummary[]> {
    return this.http.get(this.serviceLs);
  }

  createService(service: Service): Observable<ServiceSummary[]> {
    return this.http.post(this.serviceCreate, service);
  }

  getService(id: number): Observable<Service> {
    return this.http.get(this.serviceInspect + '/' + id);
  }

}
