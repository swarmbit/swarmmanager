
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { DockerServiceLogLine } from '../../../services/docker/services/docker.service.log.line';
import { DockerServiceLogFilter } from '../../../services/docker/services/docker.service.log.filter';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { BaseView } from '../../base.view';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { HeaderService } from '../../../services/header/header.service';
import { Subscription } from 'rxjs';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';
import { DockerServiceLogs } from '../../../services/docker/services/docker.service.logs';
import { BrowserService } from '../../../services/utils/browser.service';

@Component({
  selector: 'app-services-logs',
  styleUrls: ['logs.view.scss'],
  templateUrl: 'logs.view.html'
})
export class LogsView extends BaseView implements OnInit, OnDestroy {

  private subs: Subscription[] = [];

  id: string;

  logLines: DockerServiceLogLine[];

  logFilters: DockerServiceLogFilter[];

  selectedLogFilters: DockerServiceLogFilter[];

  @ViewChild('logWindow') private logWindow: ElementRef;

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private dockerServicesService: DockerServicesService,
              private snackbarService: SnackbarService,
              private browserService: BrowserService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    this.enableBackArrow();
    this.logLines = [];
    this.selectedLogFilters = [];
    this.loadFunction = this.getServiceLogs;
  }

  ngOnInit(): void {
    this.subs.push(this.route.params.subscribe(params => {
      this.id = params['name'];
      if (this.id) {
        this.setViewName(this.id + ' logs');
      }
    }));
    this.subs.push(this.route.data
      .subscribe(
        (data: Data) => {
          const logs = data['dockerServiceLogs'];
          this.initServiceLogs(logs);
        }
      ));
  }

  getServiceLogs() {
    this.subs.push(this.swarmService.getSelectedSwarm().subscribe(() => {
      if (!this.swarmService.equalsOrGreaterThenVersion29()) {
        this.snackbarService.showError('Swarm does not support service logs!');
        this.goBack(this.router, 'services');
      } else if (this.id) {
        this.dockerServicesService.getServiceLogs(this.id)
          .subscribe(
            logs => {
              this.initServiceLogs(logs);
            },
            () => {
              this.goBack(this.router, 'services');
            });
      }

    }));
  }

  initServiceLogs(logs: DockerServiceLogs): void {
    this.logLines = logs.logLines;
    this.logFilters = logs.logFilters;
    this.selectedLogFilters = [];
    setTimeout(() => {
      this.scrollToBottom();
    });
  }

  getLogLines(): DockerServiceLogLine[] {
    const logLines = [];
    for (const logLine of this.logLines) {
      if (this.checkFilter(logLine)) {
        logLines.push(logLine);
      }
    }
    return logLines;
  }

  checkFilter(logLine: DockerServiceLogLine): boolean {
    if (this.selectedLogFilters.length === 0) {
      return true;
    }
    for (const filter of this.selectedLogFilters) {
      if (logLine.taskId === filter.taskId) {
        return true;
      }
    }
    return false;
  }

  toggleFilter(clickedFilter: DockerServiceLogFilter): void {
    let isSelected = false;
    for (let i = 0; i < this.selectedLogFilters.length; i++) {
      const filter = this.selectedLogFilters[i];
      if (filter.taskId === clickedFilter.taskId) {
        isSelected = true;
        const firstPart = this.selectedLogFilters.slice(0, i);
        const secondPart = this.selectedLogFilters.slice(i + 1, this.selectedLogFilters.length);
        this.selectedLogFilters = firstPart.concat(secondPart);
        break;
      }
    }
    if (!isSelected) {
      this.selectedLogFilters.push(clickedFilter);
    }
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.subs.forEach(sub => sub.unsubscribe());
  }

  scrollToBottom(): void {
    this.logWindow.nativeElement.scrollTop = this.logWindow.nativeElement.scrollHeight;
  }

}
