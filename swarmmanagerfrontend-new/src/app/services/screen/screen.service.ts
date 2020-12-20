
import { Injectable } from '@angular/core';

@Injectable()
export class ScreenService {

  private smallWidth = 800;

  isSmall(): boolean {
    return window.innerWidth <= this.smallWidth;
  }
}
