import { Injectable } from '@angular/core';
import { Http, RequestOptions, Response} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ServiceSummary } from '../model/service.summary';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Service } from '../model/service';

@Injectable()
export class ServicesService {

  private serviceLs = '/api/service/ls';

  private serviceCreate = '/api/service/create';

  private serviceInspect = '/api/service';


  constructor (private http: Http) {}

  executeServiceLs(): Observable<ServiceSummary[]> {
    return this.http.get(this.serviceLs)
      .map((res: Response) => res.json() as ServiceSummary[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  createService(service: Service): Observable<ServiceSummary[]> {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions(headers);

    return this.http.post(this.serviceCreate, service, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getService(id: number): Observable<Service> {
    return this.http.get(this.serviceInspect + '/' + id)
      .map((res: Response) => res.json() as Service)
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
