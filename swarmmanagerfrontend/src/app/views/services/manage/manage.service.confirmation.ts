import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app--manage-service-confirmation',
  templateUrl: 'manage.service.confirmation.html',
})
export class ManageServiceConfirmation {
  dockerHubRegistry: boolean;
  privateRegisty: boolean;
  username: boolean;
  password: boolean;

  constructor(public dialogRef: MatDialogRef<ManageServiceConfirmation>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
