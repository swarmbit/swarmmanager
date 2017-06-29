import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../shell/header/header-service/header.service';
import { HeaderInfo } from '../shell/header/header-service/header.info';

@Component({
  selector: 'app-audit',
  templateUrl: 'audit.component.html'
})
export class AuditComponent {

  headerService: HeaderService;

  constructor(headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Audit';
    this.headerService.setHeaderInfo(headerInfo);
  }

}
