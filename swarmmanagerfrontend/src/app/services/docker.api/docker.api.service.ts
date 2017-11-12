import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class DockerApiService {

  private static VERSION_1_24 = 'v1.24';
  private static VERSION_1_25 = 'v1.25';
  private static VERSION_1_26 = 'v1.26';
  private static VERSION_1_27 = 'v1.27';
  private static VERSION_1_28 = 'v1.28';
  private static VERSION_1_29 = 'v1.29';
  private static VERSION_1_30 = 'v1.30';

  private dockerApiUrl = '/api/docker/version';

  private apiVersion: string;

  constructor (private http: HttpClient) {
    this.getApiVersion().then(
      version => {
        console.log(version);
      }
    );
  }

  getApiVersion(): Promise<string> {
    return new Promise((resolve, reject) => {
        if (this.apiVersion == null) {
          this.fetchApiVersion().then(
            version => {
              this.apiVersion = version;
              resolve(version);
            }
          ).catch(
            () => {
              reject();
            }
          );
        } else {
          resolve(this.apiVersion);
        }
    });
  }

  isVersion24(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_24;
  }

  isVersion25(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_25;
  }

  isVersion26(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_26;
  }

  isVersion27(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_27;
  }

  isVersion28(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_28;
  }

  isVersion29(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_29;
  }

  isVersion30(): boolean {
    return this.apiVersion == DockerApiService.VERSION_1_30;
  }

  private fetchApiVersion(): Promise<string> {
    return new Promise((resolve, reject) => {
      this.http.get(this.dockerApiUrl)
        .subscribe(
          (data: any) => {
            resolve(data);
          },
          (err: HttpErrorResponse) => {
            console.log('Error fetching docker api version: ' + err.message);
            reject();
          }
        );
    });
  }

}

