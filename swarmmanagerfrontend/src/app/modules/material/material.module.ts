
import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatCardModule, MatChipsModule, MatDialogModule, MatExpansionModule, MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatMenuModule,
  MatProgressBarModule, MatSelectModule, MatSidenavModule, MatSlideToggleModule, MatSnackBarModule,
  MatToolbarModule
} from '@angular/material';

@NgModule({
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatProgressBarModule,
    MatSidenavModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatSelectModule,
    MatSnackBarModule,
    MatChipsModule,
    MatSlideToggleModule,
    MatExpansionModule,
    MatDialogModule
  ],
  exports: [
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatProgressBarModule,
    MatSidenavModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatSelectModule,
    MatSnackBarModule,
    MatChipsModule,
    MatSlideToggleModule,
    MatExpansionModule,
    MatDialogModule
  ]
})
export class MaterialModule {
}
