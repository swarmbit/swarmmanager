import { NgModule } from '@angular/core';
import { MdButtonModule, MdInputModule, MdSliderModule, MdCardModule, MdIconModule, MdMenuModule, MdTableModule } from '@angular/material';
import { CdkTableModule } from '@angular/cdk';

@NgModule({
  imports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule, MdMenuModule, MdTableModule, CdkTableModule],
  exports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule, MdMenuModule, MdTableModule, CdkTableModule]
})
export class MaterialModule {
}
