
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { DockerServicesService } from '../../../../services/docker-services/docker.services.service';
import { ActivatedRoute } from '@angular/router';
import { HeaderService } from '../../../../services/header/header.service';
import { BaseView } from '../../../base.view';
import { LogLine } from '../../../../services/docker-services/model/log.line';
import { MdSnackBar } from '@angular/material';
import { LogFilter } from '../../../../services/docker-services/model/log.filter';

@Component({
  selector: 'app-docker-service-logs',
  styleUrls: ['docker.service.logs.view.css'],
  templateUrl: 'docker.service.logs.view.html'
})
export class DockerServiceLogsView extends BaseView implements OnInit, OnDestroy {

  private sub: any;

  id: string;

  logLines: LogLine[];

  logFilters: LogFilter[];

  selectedLogFilters: LogFilter[];

  @ViewChild('logWindow') private logWindow: ElementRef;

  constructor(private dockerServicesService: DockerServicesService,
              private route: ActivatedRoute,
              public snackBar: MdSnackBar,
              headerService: HeaderService) {
    super(headerService);
    this.enableBackArrow('/services');
    this.logLines = [];
    this.selectedLogFilters = [];
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.setViewName(this.id + ' logs');
        this.getServiceLogs(this.id);
      }
    });
  }

  getServiceLogs(id: string) {
    this.dockerServicesService.getServiceLogs(id)
      .then(
        logs => {
          this.logLines = logs.logLines;
          this.logFilters = logs.logFilters;
          this.selectedLogFilters = [];
          if (this.logLines.length === 0) {
            this.snackBar.open('Service ' + this.id + ' has no logs!', 'No Logs', {
              duration: 2000
            });
          } else {
            this.snackBar.open('Service ' + this.id + ' logs loaded!', 'Logs Loaded', {
              duration: 2000
            });
          }
          setTimeout(() => {
            this.scrollToBottom();
          });
    });
  }

  getLogLines(): LogLine[] {
    const logLines = [];
    for (const logLine of this.logLines) {
      if (this.checkFilter(logLine)) {
        logLines.push(logLine);
      }
    }
    return logLines;
  }

  checkFilter(logLine: LogLine): boolean {
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

  toggleFilter(clickedFilter: LogFilter): void {
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
    this.disableBackArrow();
    this.sub.unsubscribe();
  }

  scrollToBottom(): void {
    this.logWindow.nativeElement.scrollTop = this.logWindow.nativeElement.scrollHeight;
  }

}
