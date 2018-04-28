
import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatCardModule, MatCheckboxModule, MatChipsModule, MatDialogModule, MatExpansionModule, MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatMenuModule,
  MatProgressBarModule, MatSelectModule, MatSidenavModule, MatSlideToggleModule, MatSnackBarModule,
  MatToolbarModule, MatTooltipModule
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
    MatDialogModule,
    MatCheckboxModule,
    MatTooltipModule
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
    MatDialogModule,
    MatCheckboxModule,
    MatTooltipModule
  ]
})
export class MaterialModule {
}
