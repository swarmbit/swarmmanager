
import { Component } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';

@Component({
  selector: 'app-networks',
  templateUrl: 'networks.view.html'
})
export class NetworksView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Networks');
  }

}
