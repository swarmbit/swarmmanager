import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarm } from './docker.swarm';

@Injectable()
export class DockerSwarmService {

  private static VERSION_1_24 = 'v1.24';
  private static VERSION_1_25 = 'v1.25';
  private static VERSION_1_26 = 'v1.26';
  private static VERSION_1_27 = 'v1.27';
  private static VERSION_1_28 = 'v1.28';
  private static VERSION_1_29 = 'v1.29';
  private static VERSION_1_30 = 'v1.30';

  private dockerSwarmsUrl = '/api/swarms/';
  private selectedSwarm: DockerSwarm;
  private swarms: DockerSwarm[];

  constructor (private http: HttpClient) {
    this.swarms = [];
    this.getSwarms().then();
  }

  getSwarms(): Promise<DockerSwarm[]> {
    return new Promise((resolve, reject) => {
        if (this.swarms.length == 0) {
          this.fetchSwarms().then(
            (swarms: DockerSwarm[]) => {
              this.swarms = swarms;
              if (this.swarms.length > 0) {
                this.selectedSwarm = this.swarms[0];
              }
              resolve(this.swarms);
            }
          ).catch(
            () => {
              reject();
            }
          );
        } else {
          resolve(this.swarms);
        }
    });
  }

  getSelectSwarm(): DockerSwarm {
    return this.selectedSwarm;
  }

  selectSwarm(id: string) {
    for (const swarm of this.swarms) {
      if (id == swarm.id) {
        this.selectedSwarm = swarm;
      }
    }
  }

  isVersion24(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_24;
  }

  isVersion25(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_25;
  }

  isVersion26(): boolean {
    return this.selectedSwarm.apiVersion == DockerSwarmService.VERSION_1_26;
  }

  isVersion27(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_27;
  }

  isVersion28(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_28;
  }

  isVersion29(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_29;
  }

  isVersion30(): boolean {
    return this.getVersion() == DockerSwarmService.VERSION_1_30;
  }

  private getVersion(): string {
    if (this.selectedSwarm && this.selectedSwarm.apiVersion) {
      return this.selectedSwarm.apiVersion;
    }
    return '';
  }

  private fetchSwarms(): Promise<DockerSwarm[]> {
    return new Promise((resolve, reject) => {
      this.http.get(this.dockerSwarmsUrl)
        .subscribe(
          (data: DockerSwarm[]) => {
            resolve(data);
          },
          (err: HttpErrorResponse) => {
            console.log('Error fetching docker swarms: ' + err.message);
            reject();
          }
        );
    });
  }

}

