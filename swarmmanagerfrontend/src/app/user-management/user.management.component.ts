import { Component } from '@angular/core';
import { HeaderService } from '../shell/header/header-service/header.service';
import { HeaderInfo } from '../shell/header/header-service/header.info';

@Component({
  selector: 'app-user-management',
  templateUrl: 'user.management.component.html'
})
export class UserManagementComponent {

  headerService: HeaderService;

  constructor(headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Swarm';
    this.headerService.setHeaderInfo(headerInfo);
  }

}
