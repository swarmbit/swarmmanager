import { Component, Input, AfterViewInit, ContentChild, ElementRef, TemplateRef } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { FormsService } from '../../services/utils/forms.service';

@Component({
  selector: 'app-form-array-component',
  templateUrl: 'form.array.component.html'
})
export class FormArrayComponent {

  @Input()
  public editMode: boolean;

  @Input()
  public isDetails: boolean;

  @Input()
  public disabled: boolean;

  @Input()
  public form: FormGroup;

  @Input()
  public controlName;

  @Input()
  public entryName;

  @Input()
  public addNew;

  @Input()
  public formRowTemplate: TemplateRef<any>;

  constructor(public formsService: FormsService) {
  }

  getControlArray() {
    return this.formsService.getFormArray(this.form, this.controlName).controls;
  }

  showAdd(i): boolean {
    return (!this.isDetails || this.editMode)
      && i === (this.formsService.getFormArray(this.form, this.controlName).controls.length - 1);
  }

  showDelete(i): boolean {
    return (!this.isDetails || this.editMode)
      && i < (this.formsService.getFormArray(this.form, this.controlName).controls.length - 1);
  }

  add(): void {
      if (this.addNew) {
        this.addNew();
      } else {
        this.formsService.addOption(this.form, this.controlName, this.isDetails && !this.editMode, this.entryName);
      }
  }

  remove(i) {
    this.formsService.removeFromArray(this.form, this.controlName, i);
  }

}
