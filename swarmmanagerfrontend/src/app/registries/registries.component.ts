
import { Component } from '@angular/core';
import { HeaderService } from '../shell/header/header-service/header.service';
import { HeaderInfo } from '../shell/header/header-service/header.info';

@Component({
  selector: 'app-registries',
  templateUrl: 'registries.component.html'
})
export class RegistriesComponent {

  headerService: HeaderService;

  constructor(headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Registries';
    this.headerService.setHeaderInfo(headerInfo);
  }

}
