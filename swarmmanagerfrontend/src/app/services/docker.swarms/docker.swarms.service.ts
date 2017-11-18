import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DockerSwarm } from './docker.swarm';

@Injectable()
export class DockerSwarmService {

  private static VERSION_1_25 = 1.25;
  private static VERSION_1_26 = 1.26;
  private static VERSION_1_27 = 1.27;
  private static VERSION_1_28 = 1.28;
  private static VERSION_1_29 = 1.29;
  private static VERSION_1_30 = 1.30;

  public static DOCKER_SWARMS_URL = '/api/swarms/';
  private selectedSwarm: DockerSwarm;
  private swarms: DockerSwarm[];

  constructor (private http: HttpClient) {
    this.swarms = [];
    this.getSwarms().then();
  }

  getSwarms(): Promise<DockerSwarm[]> {
    return new Promise((resolve, reject) => {
       this.getOrFetchSwarms(resolve, reject);
    });
  }

  getSelectedSwarmId(): Promise<string> {
    return new Promise((resolve, reject) => {
      const mapToSelectedSwarm = () => {
        if (this.selectedSwarm && this.selectedSwarm.id) {
          resolve(this.selectedSwarm.id);
        }
        resolve(null);
      };
      this.getOrFetchSwarms(mapToSelectedSwarm, reject);
    });
  }

  selectSwarm(id: string) {
    for (const swarm of this.swarms) {
      if (id == swarm.id) {
        this.selectedSwarm = swarm;
      }
    }
  }

  greaterThenVersion25(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_25);
  }

  equalsOrGreaterThenVersion26(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_26);
  }

  equalsOrGreaterThenVersion27(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_27);
  }

  equalsOrGreaterThenVersion28(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_28);
  }

  equalsOrGreaterThenVersion29(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_29);
  }

  equalsOrGreaterThenVersion30(): Promise<boolean> {
    return this.compareVersions(DockerSwarmService.VERSION_1_30);
  }

  private compareVersions(versionToCompare: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      const mapToSelectedSwarm = () => {
        if (this.selectedSwarm && this.selectedSwarm.apiVersion && this.selectedSwarm.apiVersion.length > 0) {
          const version: number = +this.selectedSwarm.apiVersion.substr(1);
          if (version >= versionToCompare) {
            resolve(true);
          }
        }
        resolve(false);
      };
      this.getOrFetchSwarms(mapToSelectedSwarm, reject);
    });
  }

  private getOrFetchSwarms(resolve, reject): void {
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
  }

  private fetchSwarms(): Promise<DockerSwarm[]> {
    return new Promise((resolve, reject) => {
      this.http.get(DockerSwarmService.DOCKER_SWARMS_URL)
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

