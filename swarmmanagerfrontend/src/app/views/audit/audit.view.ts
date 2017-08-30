import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-audit',
  templateUrl: 'audit.view.html'
})
export class AuditView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'Audit');
  }

}
