import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-swarm',
  templateUrl: 'swarm.view.html'
})
export class SwarmView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Swarm');
  }

}
