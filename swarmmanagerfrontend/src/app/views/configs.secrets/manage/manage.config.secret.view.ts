import {Component, NgZone} from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BaseView } from '../../base.view';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../../../components/confirmation.dialog/confirmation.dialog.component';
import { DockerIpamConfig } from '../../../services/docker/networks/docker.ipam.config';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsService } from '../../../services/utils/forms.service';
import { BrowserService } from '../../../services/utils/browser.service';
import {DockerConfigsService} from '../../../services/docker/configs/docker.configs.service';
import {DockerSecret} from '../../../services/docker/secrets/docker.secret';
import {DockerSecretsService} from '../../../services/docker/secrets/docker.secrets.service';
import {DockerConfig} from '../../../services/docker/configs/docker.config';

@Component({
  selector: 'app-manage-config-secret',
  templateUrl: 'manage.config.secret.view.html'
})
export class ManageConfigSecretView extends BaseView {

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  form: FormGroup;
  objectName: string;
  isConfig: boolean;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private configService: DockerConfigsService,
              private secretService: DockerSecretsService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog,
              public formsService: FormsService,
              private browserService: BrowserService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    super.enableBackArrow();
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    this.isConfig = route.snapshot.data[ 'type' ] === 'config';
    if (this.isConfig) {
      this.loadFunction = this.loadConfig;
    } else {
      this.loadFunction = this.loadSecret;
    }
    this.initCreateForm();
  }

  loadConfig() {
    this.configService.getConfig(this.objectName)
      .subscribe(
        (config: any) => {
          this.initCreateForm(config);
        },
        (err: HttpErrorResponse) => {
          this.goBack(this.router, 'configs');
        });
  }

  loadSecret() {
    this.secretService.getSecret(this.objectName)
      .subscribe(
        (secret: any) => {
          this.initCreateForm(secret);
        },
        (err: HttpErrorResponse) => {
          this.goBack(this.router, 'secrets');
        });
  }

  initCreateForm(object ?: any): void {
    if (!object && this.isDetails) {
      this.subscriptions.push(this.route.data
        .subscribe(
          (data: Data) => {
            const objectData = data['object'];
            this.objectName = objectData.name;
            if (this.isConfig) {
              super.setViewName('Config ' + objectData.name);
            } else {
              super.setViewName('Secret ' + objectData.name);
            }
            this.createForm(objectData);
          }
        ));
    } else if (object) {
      this.createForm(object);
    } else {
      if (this.isConfig) {
        this.createForm(new DockerConfig());
      } else {
        this.createForm(new DockerSecret());
      }
    }
  }

  importData(data): void {
    this.form.get('data').setValue(data);
  }

  exportData(): void {
    const element = document.createElement('a');
    element.setAttribute('href', 'data:application/json;charset=utf-8,' + encodeURIComponent(this.form.get('data').value));
    element.setAttribute('download', this.objectName + '.data');
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  }

  create(): void {
    if (this.form.valid) {
      this.formInvalid = false;
      if (this.isConfig) {
        this.subscriptions.push(this.configService.createConfig(this.getObject(this.form.value)).subscribe(
          () => {
            this.router.navigate(['/configs']);
          }
        ));
      } else {
        this.subscriptions.push(this.secretService.createSecret(this.getObject(this.form.value)).subscribe(
          () => {
            this.router.navigate(['/secrets']);
          }
        ));
      }
    } else {
      this.formInvalid = true;
    }
  }

  getObjectName(): string {
    if(this.isConfig) {
      return 'config';
    } else {
      return 'secret';
    }
  }

  createForm(object: any): void {

    this.form = new FormGroup({
      'name': new FormControl({ value: object.name, disabled: this.isDetails }, [Validators.required]),
      'data': new FormControl({ value: object.data, disabled: this.isDetails }),
      'labels': new FormArray([]),
      'driverName': new FormControl({ value: object.drverName, disabled: this.isDetails }),
      'driverOptions': new FormArray([]),
      'templatingName': new FormControl({ value: object.templatingName, disabled: this.isDetails }),
      'templatingOptions': new FormArray([])
    });
    this.formsService
      .parseObjectFieldToOptions(this.form, object, 'labels', !this.isDetails, this.isDetails, 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.form, object, 'driverOptions', !this.isDetails, this.isDetails, 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.form, object, 'templatingOptions', !this.isDetails, this.isDetails, 'name', 'value');
  }

  getObject(values): any {
    let object = new DockerConfig();
    if (!this.isConfig) {
      object = new DockerSecret();
    }
    object.name = values['name'];
    object.data = values['data'];
    object.templatingName = values['templatingName'];
    this.formsService.parseOptionsToObjectField(object, values, 'templatingOptions', 'name', 'value');
    if (!this.isConfig) {
      object['driverName'] = values['driverName'];
      this.formsService.parseOptionsToObjectField(object, values, 'driverOptions', 'name', 'value');
    }
    return object;
  }

  openDialog(): void {
    let title = 'Remove Config';
    if (!this.isConfig) {
      title = 'Remove Config';
    }
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: title
      }
    });

    if (this.isConfig) {
      this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
        if (result === true) {
          this.configService.removeConfig(this.form.get('name').value)
            .subscribe(() => {
              this.router.navigate(['/configs']);
            });
        }
      }));
    } else {
      this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
        if (result === true) {
          this.secretService.removeSecret(this.form.get('name').value)
            .subscribe(() => {
              this.router.navigate(['/secrets']);
            });
        }
      }));
    }
  }

}
