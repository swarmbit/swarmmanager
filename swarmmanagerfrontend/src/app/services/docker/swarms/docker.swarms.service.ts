import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarm } from './docker.swarm';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

@Injectable()
export class DockerSwarmService {

  public static DOCKER_SWARMS_URL = '/api/swarms/';
  private selectedSwarm: DockerSwarm;
  private swarms: DockerSwarm[] = [];
  private selectedSwarmObservers: Observer<any>[] = [];

  constructor (private http: HttpClient) {
  }

  getSwarms(): Observable<DockerSwarm[]> {
    if (this.swarms.length == 0) {
      return Observable.create(observer => {
        this.fetchSwarms().subscribe(
          (swarms: DockerSwarm[]) => {
            this.swarms = swarms;
            if (this.swarms.length > 0) {
              this.selectSwarm(this.swarms[0].id);
            }
            observer.next(this.swarms);
            observer.complete();
          });
      });
    } else {
      return Observable.create(observer => {
        if (this.swarms.length > 0) {
          this.selectSwarm(this.swarms[0].id);
        }
        observer.next(this.swarms);
        observer.complete();
      });
    }
  }

  getSelectedSwarm(): Observable<DockerSwarm> {
    return Observable.create(observer => {
      if (this.selectedSwarm != null) {
        observer.next(this.selectedSwarm);
      }
      this.selectedSwarmObservers.push(observer);
    });
  }

  selectSwarm(id: string) {
    for (const swarm of this.swarms) {
      if (id == swarm.id) {
        this.selectedSwarm = swarm;
        for (const observer of this.selectedSwarmObservers) {
          observer.next(this.selectedSwarm);
        }
      }
    }
  }

  private fetchSwarms(): Observable<DockerSwarm[]> {
      return this.http.get<DockerSwarm[]>(DockerSwarmService.DOCKER_SWARMS_URL);
  }

}

