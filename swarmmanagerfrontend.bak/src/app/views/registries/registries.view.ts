
import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-registries',
  templateUrl: 'registries.view.html'
})
export class RegistriesComponent extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Registries');
  }

}
