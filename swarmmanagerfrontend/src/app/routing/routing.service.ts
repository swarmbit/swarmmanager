import { Injectable } from '@angular/core';
import {BackArrow} from './back.arrow';

@Injectable()
export class RoutingService {

  private headerName: string;

  private backArrow: BackArrow;

  public setHeaderName(headerName: string): void {
    this.headerName = headerName;
  }

  public getHeaderName(): string {
    return this.headerName;
  }

  public setBackArrow(backArrow: BackArrow): void {
    this.backArrow = backArrow;
  }

  public getBackArrow(): BackArrow {
    return this.backArrow;
  }

}
