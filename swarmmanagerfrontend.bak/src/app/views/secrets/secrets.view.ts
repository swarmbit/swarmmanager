
import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';

@Component({
  selector: 'app-secrets',
  templateUrl: 'secrets.view.html'
})
export class SecretsView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Secrets');
  }

}
