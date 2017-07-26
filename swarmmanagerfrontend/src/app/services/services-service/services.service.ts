import { Injectable } from '@angular/core';
import { Http, RequestOptions, Response, Headers} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ServiceSummary } from '../model/service.summary';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Service } from '../model/service';
import { AuthService } from '../../auth/auth.service';

@Injectable()
export class ServicesService {

  private serviceLs = '/api/service/ls';

  private serviceCreate = '/api/service/create';

  private serviceInspect = '/api/service';

  constructor (private http: Http) {}

  executeServiceLs(): Observable<ServiceSummary[]> {
    const headers = new Headers();
    headers.append(AuthService.AUTH_HEADER, localStorage.getItem(AuthService.AUTH_HEADER));
    const options = new RequestOptions();
    options.headers = headers;
    return this.http.get(this.serviceLs, options)
      .map((res: Response) => res.json() as ServiceSummary[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  createService(service: Service): Observable<ServiceSummary[]> {
    const headers = new Headers({
      'Content-Type': 'application/json',
    });
    const options = new RequestOptions();
    options.headers = headers;
    return this.http.post(this.serviceCreate, service, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getService(id: number): Observable<Service> {
    const headers = new Headers();
    headers.append(AuthService.AUTH_HEADER, localStorage.getItem(AuthService.AUTH_HEADER));
    const options = new RequestOptions();
    options.headers = headers;
    return this.http.get(this.serviceInspect + '/' + id, options)
      .map((res: Response) => res.json() as Service)
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
