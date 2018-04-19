import { Component, OnDestroy } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ViewUtils } from '../view.utils';
import { ActivatedRoute } from '@angular/router';
import { BrowserService } from '../../services/utils/browser.service';
import {DockerConfig} from '../../services/docker/configs/docker.config';
import {DockerConfigService} from '../../services/docker/configs/docker.configs.service';
import {DockerSecretsService} from '../../services/docker/secrets/docker.secrets.service';
import {DockerSecret} from '../../services/docker/secrets/docker.secret';

@Component({
  selector: 'app-configs',
  styleUrls: ['configs.secrets.view.scss'],
  templateUrl: 'configs.secrets.view.html'
})
export class ConfigsSecretsView extends BaseView {

  configs: DockerConfig[] = [];
  secrets: DockerSecret[] = [];

  filter = '';
  isConfig: boolean;

  constructor(headerService: HeaderService,
              private swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private configService: DockerConfigService,
              private secretsService: DockerSecretsService,
              private browserService: BrowserService
              ) {
    super(headerService, route, swarmService, userService, browserService);
    this.isConfig = route.snapshot.data[ 'type' ] === 'config';
    if (this.isConfig) {
      this.refreshFunction = this.refreshConfigs;
      this.refreshConfigs(true);
    } else {
      this.refreshFunction = this.refreshSecrets;
      this.refreshSecrets(true);
    }
  }

  getCreateButtonText(): string {
    if (this.isConfig) {
      return 'Create Config';
    } else {
      return 'Create Secret'
    }
  }

  getRefreshButtonText(): string {
    if (this.isConfig) {
      return 'Refresh Configs List';
    } else {
      return 'Refresh Secrets List';
    }
  }

  getFilterMessage(): string {
    if (this.isConfig) {
      return 'Filter by config name'
    } else {
      return 'Filter by secret name'
    }
  }

  refreshConfigs(noMessage?: boolean): void {
    super.addSubscription(this.configService.getConfigsList(noMessage).subscribe(
      (configs: DockerConfig[]) => {
        this.configs = configs;
      }
    ));
  }

  refreshSecrets(noMessage?: boolean): void {
    super.addSubscription(this.secretsService.getSecretsList(noMessage).subscribe(
      (secrets: DockerSecret[]) => {
        this.secrets = secrets;
      }
    ));
  }

  getObjects(): any[] {
    if (this.isConfig) {
      return ViewUtils.getFilteredArray(this.filter, this.configs, 'name');
    } else {
      return ViewUtils.getFilteredArray(this.filter, this.secrets, 'name');
    }
  }

  objectType(): string {
    if (this.isConfig) {
      return 'configs';
    } else {
      return 'secrets';
    }
  }

}
