
import { Component } from '@angular/core';
import { HeaderService } from '../shell/header/header-service/header.service';
import { HeaderInfo } from '../shell/header/header-service/header.info';

@Component({
  selector: 'app-nodes',
  templateUrl: 'nodes.component.html'
})
export class NodesComponent {

  headerService: HeaderService;

  constructor(headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Nodes';
    this.headerService.setHeaderInfo(headerInfo);
  }

}
