import { Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ScreenService } from '../../services/screen/screen.service';
import { NavigationItem } from './model/navigation.item';
import { HeaderService } from '../../services/header/header.service';
import { Subscription } from 'rxjs';
import { HeaderInfo } from '../../services/header/header.info';
import { MatSidenav } from '@angular/material/sidenav';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { DockerSwarm } from '../../services/docker/swarms/docker.swarm';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shell',
  styleUrls: ['shell.component.scss'],
  templateUrl: 'shell.component.html'
})
export class ShellComponent implements OnInit, OnDestroy {

  SWIPE_ACTION = { LEFT: 'swipeleft', RIGHT: 'swiperight' };
  navbarModeOver = 'over';
  navbarModeSide = 'side';
  isSmall: boolean;
  navigationItems: NavigationItem[];
  headerInfo: HeaderInfo;
  selectedViewName: string;
  swarms: DockerSwarm[];
  selectedSwarm = '';

  private subscription: Subscription;
  @ViewChild('sidenav') private sidenav: MatSidenav;

  public constructor(private router: Router,
                     public screenService: ScreenService,
                     private headerService: HeaderService,
                     private swarmService: DockerSwarmService) {
    this.navigationItems = [];
    this.navigationItems.push(new NavigationItem('Networks', '/networks', 'router'));
    this.navigationItems.push(new NavigationItem('Nodes', '/nodes', 'device_hub'));
    this.subscription = this.headerService.getHeaderInfo().subscribe(headerInfo => {
      this.headerInfo = headerInfo;
      this.selectedViewName = headerInfo.currentViewName;
    });
    this.swarms = [];
    this.swarmService.getSwarms().subscribe(
      swarms => {
        this.swarms = swarms;
        if (this.swarms && this.swarms.length > 0) {
          this.selectedSwarm = this.swarms[0].id;
        }
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

    if (this.isBackArrowActive()) {
      cssClass += ' show';
    }
    return cssClass;
  }

  isBackArrowActive() {
    return this.headerInfo && this.headerInfo.backArrow && this.headerInfo.backArrow.show;
  }

  goBack() {
    if (this.headerInfo && this.headerInfo.backArrow) {
      this.router.navigate([this.headerInfo.backArrow.link]);
    }
  }

  closeNavbar() {
    if (this.screenService.isSmall()) {
      this.sidenav.close();
    }
  }

  swipe(action) {
    console.log(action);
    if (action === this.SWIPE_ACTION.RIGHT) {
      if (this.screenService.isSmall()) {
        this.sidenav.open();
      }
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isSmall = this.screenService.isSmall();
  }

  selectSwarm(event) {
    this.swarmService.selectSwarm(event.value);
    this.closeNavbar();
  }

}

