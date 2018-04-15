import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: 'reset.key.dialog.component.html',
})
export class ResetKeyDialogComponent {

  constructor(public dialogRef: MatDialogRef<ResetKeyDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

}
