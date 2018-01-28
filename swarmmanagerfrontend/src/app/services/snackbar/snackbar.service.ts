import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';

@Injectable()
export class SnackbarService {

  constructor(public snackBar: MatSnackBar) {
  }

  showSuccess(message: string): void {
    const config = new MatSnackBarConfig();
    config.duration = 2000;
    this.snackBar.open(message, 'Dismiss', config);
  }

  showError(message: string): void {
    const config = new MatSnackBarConfig();
    config.duration = 2000;
    this.snackBar.open(message, 'Dismiss', config);
  }
}
