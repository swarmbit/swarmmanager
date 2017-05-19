import { NgModule } from '@angular/core';
import { MdButtonModule, MdInputModule, MdSliderModule, MdCardModule, MdIconModule } from '@angular/material';

@NgModule({
  imports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule],
  exports: [MdButtonModule, MdSliderModule, MdInputModule, MdCardModule, MdIconModule]
})
export class MaterialModule {
}
