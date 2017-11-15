
import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-nodes',
  templateUrl: 'nodes.view.html'
})
export class NodesView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Nodes');
    super.enableBackArrow('/nodes');
  }

}
