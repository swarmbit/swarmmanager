import { HeaderService } from '../services/header/header.service';
import { HeaderInfo } from '../services/header/header.info';
import { BackArrow } from '../services/header/back.arrow';
import { User } from '../services/user/model/user';
import { DockerSwarmService } from '../services/docker/swarms/docker.swarms.service';
import { UserService } from '../services/user/user.service';
import { OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

export class BaseView implements OnDestroy {

  headerService: HeaderService;
  headerInfo: HeaderInfo;
  user: User;
  refreshFunction: Function;
  subscriptions: Subscription[];

  constructor(headerService: HeaderService,
              viewName: string,
              swarmService: DockerSwarmService,
              userService: UserService) {
    this.subscriptions = [];
    this.user = new User();
    this.headerService = headerService;
    this.headerInfo = new HeaderInfo();
    this.headerInfo.currentViewName = '';
    this.headerInfo.currentViewName = viewName;
    this.headerService.setHeaderInfo(this.headerInfo);
    userService.getUser(false).then(
      user => {
        this.user = user;
      }
    );
    this.subscriptions.push(swarmService.getSelectedSwarm().subscribe(
      () => {
        if (this.refreshFunction) {
          this.refreshFunction();
        }
      }
    ));
  }

  setViewName(viewName: string): void {
    this.headerInfo.currentViewName = viewName;
    this.headerService.setHeaderInfo(this.headerInfo);
  }

  enableBackArrow(link: string): void {
    this.headerInfo.backArrow = new BackArrow(true, link);
    this.headerService.setHeaderInfo(this.headerInfo);
  }

  disableBackArrow(): void {
    this.headerInfo.backArrow = new BackArrow(false, '');
    this.headerService.setHeaderInfo(this.headerInfo);
  }

  addSubscription(subscription: Subscription): void {
   this.subscriptions.push(subscription);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    });
  }

}
