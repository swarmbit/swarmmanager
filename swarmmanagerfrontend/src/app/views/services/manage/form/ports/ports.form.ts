import { PortsFormUtil } from './ports.form.util';
import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormsService } from '../../../../../services/utils/forms.service';

@Component({
  selector: 'app-ports-form',
  templateUrl: 'ports.form.html'
})
export class PortsForm {

  @Input()
  public editMode: boolean;

  @Input()
  public isDetails: boolean;

  @Input()
  public disabled: boolean;

  @Input()
  public serviceForm: FormGroup;

  public controlName = PortsFormUtil.controlName;

  public add = () => {
    PortsFormUtil.addPort(this.serviceForm, null, false);
  }

  constructor(public formsService: FormsService) {
  }

}
