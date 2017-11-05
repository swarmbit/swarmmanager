import { Component } from '@angular/core';
import { HeaderService } from '../../services/header/header.service';
import { BaseView } from '../base.view';

@Component({
  selector: 'app-user-management',
  templateUrl: 'user.management.view.html'
})
export class UserManagementView extends BaseView {

  constructor(headerService: HeaderService) {
    super(headerService, 'User Management');
  }

}
