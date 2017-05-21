import { NgModule } from '@angular/core';
import {
  MdButtonModule, MdInputModule, MdSliderModule, MdCardModule, MdIconModule,
  MdMenuModule
} from '@angular/material';

@NgModule({
  imports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule, MdMenuModule],
  exports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule, MdMenuModule]
})
export class MaterialModule {
}
