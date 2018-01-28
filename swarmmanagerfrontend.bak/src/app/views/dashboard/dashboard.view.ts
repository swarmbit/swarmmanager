import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'dashboard.view.html'
})
export class DashboardView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Dashboard');
  }

}
