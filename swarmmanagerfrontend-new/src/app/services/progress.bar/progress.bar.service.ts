import { Injectable } from '@angular/core';

@Injectable()
export class ProgressBarService {

  private value = 0;

  private visible = false;

  getValue(): number {
    return this.value;
  }

  setValue(value: number): void  {
    this.value = value;
  }

  isVisible(): boolean {
    return this.visible;
  }

  showProgessBar(): void {
    this.visible = true;
  }

  hideProgessBar(): void {
    this.visible = false;
  }
}
