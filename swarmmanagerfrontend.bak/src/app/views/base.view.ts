import { HeaderService } from '../services/header/header.service';
import { HeaderInfo } from '../services/header/header.info';
import { BackArrow } from '../services/header/back.arrow';

export class BaseView {

  headerService: HeaderService;
  headerInfo: HeaderInfo;

  constructor(headerService: HeaderService, viewName?: string) {
    this.headerService = headerService;
    this.headerInfo = new HeaderInfo();
    this.headerInfo.currentViewName = '';
    if (viewName) {
      this.headerInfo.currentViewName = viewName;
    }
    this.headerService.setHeaderInfo(this.headerInfo);
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
