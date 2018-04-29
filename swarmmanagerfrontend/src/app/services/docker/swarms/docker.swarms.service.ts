import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarm } from './docker.swarm';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

@Injectable()
export class DockerSwarmService {

  public static VERSION_1_25 = 1.25;
  public static VERSION_1_26 = 1.26;
  public static VERSION_1_27 = 1.27;
  public static VERSION_1_28 = 1.28;
  public static VERSION_1_29 = 1.29;
  public static VERSION_1_30 = 1.30;
  public static VERSION_1_31 = 1.31;
  public static DOCKER_SWARMS_URL = '/api/swarms/';
  private selectedSwarm: DockerSwarm;
  private swarms: DockerSwarm[] = [];
  private selectedSwarmObservers: Observer<DockerSwarm>[] = [];
  private swarmChangeObservers: Observer<DockerSwarm>[] = [];

  constructor (private http: HttpClient) {
  }

  isSwarmSelected(): boolean {
    return this.selectedSwarm != null;
  }

  getSwarms(): Observable<DockerSwarm[]> {
    if (this.swarms.length === 0) {
      return Observable.create(observer => {
        this.fetchSwarms().subscribe(
          (swarms: DockerSwarm[]) => {
            this.swarms = swarms;
            if (!this.isSwarmSelected() && this.swarms.length > 0) {
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
        observer.complete();
      }
      this.selectedSwarmObservers.push(observer);
    });
  }

  onSwarmChange(): Observable<DockerSwarm> {
    return Observable.create(observer => {
      if (this.selectedSwarm != null) {
        observer.next(this.selectedSwarm);
      }
      this.swarmChangeObservers.push(observer);
    });
  }

  selectSwarm(id: string) {
    for (const swarm of this.swarms) {
      if (id === swarm.id) {
        this.selectedSwarm = swarm;
        for (const observer of this.selectedSwarmObservers) {
          observer.next(this.selectedSwarm);
          observer.complete();
        }
        for (const observer of this.swarmChangeObservers) {
          observer.next(this.selectedSwarm);
        }
      }
    }
  }

  private fetchSwarms(): Observable<DockerSwarm[]> {
      return this.http.get<DockerSwarm[]>(DockerSwarmService.DOCKER_SWARMS_URL);
  }

  public equalsOrGreaterThenVersion25(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_25);
  }

  public equalsOrGreaterThenVersion26(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_26);
  }

  public equalsOrGreaterThenVersion27(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_27);
  }

  public equalsOrGreaterThenVersion28(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_28);
  }

  public equalsOrGreaterThenVersion29(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_29);
  }

  public equalsOrGreaterThenVersion30(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_30);
  }

  public equalsOrGreaterThenVersion31(): boolean {
    return this.compareVersions(DockerSwarmService.VERSION_1_31);
  }

  private compareVersions(versionToCompare: number): boolean {
    if (this.selectedSwarm && this.selectedSwarm.apiVersion) {
      const version: number = +this.selectedSwarm.apiVersion.substr(1);
      if (version >= versionToCompare) {
        return true;
      }
    }
    return false;
  }

}

