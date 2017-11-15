import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ScreenService } from '../../services/screen/screen.service';
import { NavigationItem } from './model/navigation.item';
import { HeaderService } from '../../services/header/header.service';
import { Subscription } from 'rxjs';
import { HeaderInfo } from '../../services/header/header.info';
import { MatSidenav } from '@angular/material/sidenav';


@Component({
  selector: 'app-shell',
  styleUrls: ['shell.component.scss'],
  templateUrl: 'shell.component.html'
})
export class ShellComponent implements OnInit, OnDestroy {

  navbarModeOver = 'over';
  navbarModeSide = 'side';
  isSmall: boolean;
  navigationItems: NavigationItem[];
  headerInfo: HeaderInfo;
  selectedViewName: string;
  private subscription: Subscription;
  @ViewChild('sidenav') private sidenav: MatSidenav;


  constructor(public screenService: ScreenService, private headerService: HeaderService) {
    this.navigationItems = [];
    this.navigationItems.push(new NavigationItem('Networks', '/networks', 'router'));
    this.navigationItems.push(new NavigationItem('Nodes', '/nodes', 'device_hub'));
    this.subscription = this.headerService.getHeaderInfo().subscribe(headerInfo => {
      this.headerInfo = headerInfo;
      this.selectedViewName = headerInfo.currentViewName;
    });
  }

  ngOnInit(): void {
    this.isSmall = this.screenService.isSmall();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getNavbarMode() {
    if (this.screenService.isSmall()) {
      return this.navbarModeOver;
    }
    return this.navbarModeSide;
  }

  getSelectedViewName() {
    if (this.headerInfo && this.headerInfo.currentViewName) {
      return this.headerInfo.currentViewName;
    }
    return '';
  }

  showBackArrow() {
    let cssClass = 'material-icons arrow-back';

    if (this.headerInfo && this.headerInfo.backArrow && this.headerInfo.backArrow.show) {
      cssClass += ' show';
    }
    return cssClass;
  }

  goBack() {
    if (this.headerInfo && this.headerInfo.backArrow) {
    }
  }

  closeNavbar() {
    if (this.screenService.isSmall()) {
      this.sidenav.close();
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isSmall = this.screenService.isSmall();
  }

}

