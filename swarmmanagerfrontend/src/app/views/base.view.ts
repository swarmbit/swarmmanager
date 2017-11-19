import { HeaderService } from '../services/header/header.service';
import { HeaderInfo } from '../services/header/header.info';
import { BackArrow } from '../services/header/back.arrow';
import { User } from '../services/user/model/user';
import { DockerSwarmService } from '../services/docker.swarms/docker.swarms.service';
import { UserService } from '../services/user/user.service';

export class BaseView {

  headerService: HeaderService;
  headerInfo: HeaderInfo;
  user: User;
  refreshFunction: Function;

  constructor(headerService: HeaderService,
              viewName: string,
              swarmService: DockerSwarmService,
              userService: UserService) {
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
    swarmService.getSelectedSwarm().subscribe(
      () => {
        if (this.refreshFunction) {
          this.refreshFunction();
        }
      }
    );
  }

  setViewName(viewName: string): void {
    this.headerInfo.currentViewName = viewName;
    this.headerService.setHeaderInfo(this.headerInfo);
  }

  enableBackArrow(link: string): void {
    this.headerInfo.backArrow = new BackArrow(true, link);
    this.headerService.setHeaderInfo(this.headerInfo);
  }

  disableBackArrow() {
    this.headerInfo.backArrow = new BackArrow(false, '');
    this.headerService.setHeaderInfo(this.headerInfo);
  }

}
