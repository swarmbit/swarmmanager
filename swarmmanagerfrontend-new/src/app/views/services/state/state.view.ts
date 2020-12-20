import { Component, OnDestroy, OnInit } from '@angular/core';
import { DockerServiceTask } from '../../../services/docker/services/docker.service.task';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { BaseView } from '../../base.view';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { HeaderService } from '../../../services/header/header.service';
import { Subscription } from 'rxjs';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerServiceState } from '../../../services/docker/services/docker.service.state';

@Component({
  selector: 'app-services-state',
  styleUrls: ['state.view.scss'],
  templateUrl: 'state.view.html'
})
export class StateView extends BaseView implements OnInit, OnDestroy {

  private subs: Subscription[] = [];

  id: string;

  displayedColumns = ['id', 'image', 'nodeHostname', 'desiredState', 'state', 'errorMessage'];

  keys: Set<string> = new Set();
  tasksMap: Map<string, DockerServiceTask[]> = new Map();

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
    this.loadFunction = this.getServiceState;
  }

  ngOnInit(): void {
    this.subs.push(this.route.params.subscribe(params => {
      this.id = params['name'];
      if (this.id) {
        this.setViewName(this.id + ' state');
      }
    }));
    this.subs.push(this.route.data
      .subscribe(
        (data: Data) => {
          const tasks = data['dockerServiceState'];
          this.initServiceState(tasks);
        }
      ));
  }

  getTasks(key: string): DockerServiceTask[] {
      return this.tasksMap.get(key);
  }

  getServiceState() {
    this.subs.push(this.swarmService.getSelectedSwarm().subscribe(() => {
      if (this.id) {
        this.dockerServicesService.getServiceState(this.id)
          .subscribe(
            state => {
              this.initServiceState(state);
            },
            () => {
              this.goBack(this.router, 'services');
            });
      }
    }));
  }

  initServiceState(state: DockerServiceState): void {
    this.tasksMap = new Map();
    if (state && state.tasks) {
      state.tasks.forEach(task => {
        let key = 'Replica ' + task.replica;
        if (!task.replica) {
          key = 'Node ' + task.nodeHostname;
        }
        let tasks = this.tasksMap.get(key);
        if (!tasks) {
          tasks = [];
          this.tasksMap.set(key, tasks);
        }
        tasks.push(task);
        this.keys.add(key);
      });
    }

  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
