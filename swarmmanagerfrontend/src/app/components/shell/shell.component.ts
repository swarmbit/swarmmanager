
import { Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ScreenService } from '../../services/screen/screen.service';
import { NavigationItem } from './model/navigation.item';
import { HeaderService } from '../../services/header/header.service';
import { Subscription } from 'rxjs/Subscription';
import { HeaderInfo } from '../../services/header/header.info';
import { MatSidenav } from '@angular/material/sidenav';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { DockerSwarm } from '../../services/docker/swarms/docker.swarm';
import { Router } from '@angular/router';
import { BrowserService } from '../../services/utils/browser.service';
import { UserService } from '../../services/user/user.service';
import { User } from '../../services/user/user';

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
  user: User;

  private subscription: Subscription;
  @ViewChild('sidenav') private sidenav: MatSidenav;

  public constructor(private router: Router,
                     public screenService: ScreenService,
                     private headerService: HeaderService,
                     private swarmService: DockerSwarmService,
                     private browserService: BrowserService,
                     private userService: UserService) {
    this.navigationItems = [];
    this.userService.getUser().then((user) => {
      this.user = user;
      if (user.isVisitor()) {
        this.swarmService.getSwarms().subscribe(
          swarms => {
            this.swarms = swarms;
            if (this.swarms && this.swarms.length > 0) {
              this.selectedSwarm = this.swarms[0].id;
            }
        });
        this.swarmService.onSwarmChange().subscribe(
          () => {
            this.navigationItems = [];
            this.navigationItems.push(new NavigationItem('Services', '/services', 'cloud'));

            if (this.swarmService.equalsOrGreaterThenVersion25()) {
              this.navigationItems.push(new NavigationItem('Secrets', '/secrets', 'lock'));
            }
            if (this.swarmService.equalsOrGreaterThenVersion30()) {
              this.navigationItems.push(new NavigationItem('Configs', '/configs', 'description'));
            }
            this.navigationItems.push(new NavigationItem('Networks', '/networks', 'router'));
            this.navigationItems.push(new NavigationItem('Nodes', '/nodes', 'device_hub'));
            if (user.isAdmin()) {
              this.navigationItems.push(new NavigationItem('User Management', '/users', 'account_box'));
            }
          });
      }
    });
    this.subscription = this.headerService.getHeaderInfo().subscribe(headerInfo => {
      this.headerInfo = headerInfo;
      this.selectedViewName = headerInfo.currentViewName;
    });
    this.swarms = [];
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
    return this.headerInfo && this.headerInfo.backArrow && this.headerInfo.backArrow.show && !this.browserService.cannotGoBack();
  }

  goBack() {
    if (this.headerInfo && this.headerInfo.backArrow) {
      this.router.navigate([this.browserService.getBackUrl()]);
    }
  }

  closeNavbar() {
    if (this.screenService.isSmall()) {
      this.sidenav.close();
    }
  }

  swipe(action) {
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

