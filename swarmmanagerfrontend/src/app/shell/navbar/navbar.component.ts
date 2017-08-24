import { Component } from '@angular/core';
import { NavigationItem } from '../navigation.item';

@Component({
  selector: 'app-navbar',
  styleUrls: ['navbar.scss'],
  templateUrl: 'navbar.component.html'
})
export class SidenavComponent {

  navigationItems: NavigationItem[];

  constructor() {
    this.navigationItems = [];
    this.navigationItems.push(new NavigationItem('Dashboard', '/dashboard', 'dashboard'));
    this.navigationItems.push(new NavigationItem('Services', '/services', 'cloud'));
    this.navigationItems.push(new NavigationItem('Swarm', '/swarm', 'computer'));
    this.navigationItems.push(new NavigationItem('Nodes', '/nodes', 'device_hub'));
    this.navigationItems.push(new NavigationItem('Networks', '/networks', 'router'));
    this.navigationItems.push(new NavigationItem('Secrets', '/secrets', 'lock'));
    this.navigationItems.push(new NavigationItem('Registries', '/registries', 'archive'));
    this.navigationItems.push(new NavigationItem('Audit', '/audit', 'notifications_none'));
    this.navigationItems.push(new NavigationItem('User Management', '/users', 'people'));
  }

}
