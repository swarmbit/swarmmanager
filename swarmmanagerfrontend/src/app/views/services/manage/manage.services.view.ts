import { NetworkFormUtil } from './form/networks/network.form.util';
import { PortsFormUtil } from './form/ports/ports.form.util';
import { NetworksForm } from './form/networks/networks.form';
import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { BaseView } from '../../base.view';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../../components/confirmation.dialog/confirmation.dialog.component';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { DockerService } from '../../../services/docker/services/docker.service';
import { CleanServiceImagePipe } from '../../../pipes/clean.service.image.pipe';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsService } from '../../../services/utils/forms.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { ManageServiceConfirmation } from './manage.service.confirmation';
import { isNumber } from 'util';
import { BindMountOptions, DockerServiceMount, DockerTmpfsMountOptions,
  DockerVolumeMountOptions } from '../../../services/docker/services/docker.service.mount';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';
import { BrowserService } from '../../../services/utils/browser.service';
import { DockerConfigsService } from '../../../services/docker/configs/docker.configs.service';
import { DockerConfig } from '../../../services/docker/configs/docker.config';
import { DockerSecret } from '../../../services/docker/secrets/docker.secret';
import { DockerServiceSecretAndConfig } from '../../../services/docker/services/docker.service.secrect.and.config';
import { DockerSecretsService } from '../../../services/docker/secrets/docker.secrets.service';

@Component({
  selector: 'app-manage-service',
  styleUrls: ['manage.services.view.scss'],
  templateUrl: 'manage.services.view.html'
})
export class ManageServicesView extends BaseView implements OnInit {

  @ViewChild('networksForm')
  networksForm: NetworksForm;

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  editMode: boolean;
  serviceForm: FormGroup;
  serviceName: string;
  isGlobalService: boolean;
  configs: DockerConfig[] = [];
  secrets: DockerSecret[] = [];
  service: DockerService;

  previousTimeFields = {
    restartDelayUnit: 'ns',
    restartWindowUnit: 'ns',
    healthIntervalUnit: 'ns',
    healthTimeoutUnit: 'ns',
    healthStartPeriodUnit: 'ns',
    updateDelayUnit: 'ns',
    updateMonitorUnit: 'ns',
    rollbackDelayUnit: 'ns',
    rollbackMonitorUnit: 'ns',
    stopGracePeriodUnit: 'ns'
  };

  previousMemoryFields = {
    reserveMemoryUnit: 'Bytes',
    limitMemoryUnit: 'Bytes',
    sizeUnit: 'Bytes'
  };

  constructor(headerService: HeaderService,
              private route: ActivatedRoute,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private browserService: BrowserService,
              private dockerServicesService: DockerServicesService,
              private router: Router,
              public dialog: MatDialog,
              public formsService: FormsService,
              private networkService: DockerNetworksService,
              private configsService: DockerConfigsService,
              private secretsService: DockerSecretsService,
              private snackbarService: SnackbarService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    super.enableBackArrow();
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    this.loadFunction = this.loadService;
  }

  ngOnInit(): void {
    this.initCreateForm();
    this.initExternalDockerObjects();
  }

  initCreateForm(dockerService ?: DockerService): void {
    this.formInvalid = false;
    if (!dockerService && this.isDetails) {
      this.subscriptions.push(this.route.data
        .subscribe(
          (data: Data) => {
            const dockerServiceData = data['dockerService'];
            this.serviceName = dockerServiceData.name;
            super.setViewName('Service ' + dockerServiceData.name);
            this.isGlobalService = dockerServiceData.global;
            this.createForm(dockerServiceData);
          }
        ));
    } else if (dockerService) {
      this.isGlobalService = dockerService.global;
      this.createForm(dockerService);
    } else {
      this.createForm(this.getDockerServiceWithDefaults());
    }
  }

  initExternalDockerObjects(): void {
    this.subscriptions.push(this.swarmService.onSwarmChange().subscribe(() => {
      if (this.swarmService.equalsOrGreaterThenVersion25()) {
        this.secretsService.getSecretsList(true).subscribe(
          secrets => {
            this.secrets = [];
            for (const secret of secrets) {
              this.secrets.push(secret);
            }
          }
        );
      }
      if (this.swarmService.equalsOrGreaterThenVersion30()) {
        this.configsService.getConfigsList(true).subscribe(
          configs => {
            this.configs = [];
            for (const config of configs) {
              this.configs.push(config);
            }
          }
        );
      }
    }));
  }

  loadService(noMessage?: boolean) {
    if (this.isDetails && this.serviceName) {
      this.dockerServicesService.getService(this.serviceName, noMessage)
        .subscribe(
          (dockerService: DockerService) => {
            this.initCreateForm(dockerService);
          },
          (err: HttpErrorResponse) => {
            this.goBack(this.router, 'services');
          });
    }
  }

  getDockerServiceWithDefaults(): DockerService {
    const dockerService = new DockerService();
    dockerService.replicas = 1;
    dockerService.endpointMode = 'vip';
    dockerService.restartMaxAttempts = 0;
    dockerService.restartCondition = 'any';
    dockerService.restartDelay = 5000000000;
    dockerService.restartWindow = 0;
    dockerService.updateFailureAction = 'pause';
    dockerService.updateMaxFailureRatio = 0;
    dockerService.updateMonitor = 5000000000;
    dockerService.updateOrder = 'stop-first';
    dockerService.updateParallelism = 1;
    dockerService.rollbackFailureAction = 'pause';
    dockerService.rollbackMonitor = 5000000000;
    dockerService.rollbackOrder = 'stop-first';
    dockerService.rollbackParallelism = 1;
    dockerService.rollbackMaxFailureRatio = 0;
    dockerService.stopGracePeriod = 10000000000;
    return dockerService;
  }

  createService(): void {
    if (this.serviceForm.valid) {
      this.formInvalid = false;
      const dialogRef = this.dialog.open(ManageServiceConfirmation, {
        width: '20rem',
        data: {
          title: 'Create service',
          registryToggle: 'Private Registry'
        }
      });

      this.subscriptions.push(dialogRef.afterClosed().subscribe(confirmation => {
        if (confirmation.result === true) {
          const dockerService = this.getNewDockerService(this.serviceForm.value);
          if (confirmation['privateRegistry']) {
            dockerService.registryUsername = confirmation.username;
            dockerService.registryPassword = btoa(confirmation.password);
            dockerService.dockerHubRegistry = confirmation.dockerHubRegistry;
          }
          this.subscriptions.push(this.dockerServicesService.createService(dockerService).subscribe(
            () => {
              this.router.navigate(['/services']);
            }
          ));
        }
      }));
    } else {
      this.formInvalid = true;
    }
  }

  isDisabled() {
    return this.isDetails && !this.editMode;
  }

  createForm(dockerService: DockerService): void {
    this.service = dockerService;
    const imagePipe = new CleanServiceImagePipe();
    this.serviceForm = new FormGroup({
      'name': new FormControl({ value: dockerService.name, disabled: this.isDetails }, [Validators.required]),
      'image': new FormControl({ value: imagePipe.transform(dockerService.image), disabled: this.isDisabled() }, [Validators.required]),
      'global': new FormControl({ value: dockerService.global, disabled:  this.isDetails }),
      'replicas': new FormControl({ value: dockerService.replicas, disabled: this.isDisabled() || this.isGlobalService },
        [Validators.required, Validators.min(0)]),
      'env': new FormArray([]),
      'labels':  new FormArray([]),
      'containerLabels':  new FormArray([]),
      'constraints':  new FormArray([]),
      'placementPreferences':  new FormArray([]),
      'restartCondition': new FormControl({ value: dockerService.restartCondition, disabled:  this.isDisabled() }),
      'restartMaxAttempts': new FormControl({ value: dockerService.restartMaxAttempts, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'restartDelay': new FormControl({ value: dockerService.restartDelay, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'restartDelayUnit': new FormControl({ value: 'ns', disabled:  this.isDetails }),
      'restartWindow': new FormControl({ value: dockerService.restartWindow, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'restartWindowUnit': new FormControl({ value: 'ns', disabled:  this.isDetails }),
      'healthCmd': new FormControl({ value: dockerService.healthCmd, disabled:  this.isDisabled() }),
      'healthRetries': new FormControl({ value: dockerService.healthRetries, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'healthInterval': new FormControl({ value: dockerService.healthInterval, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'healthIntervalUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'healthTimeout': new FormControl({ value: dockerService.healthTimeout, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'healthTimeoutUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'healthStartPeriod': new FormControl({ value: dockerService.healthStartPeriod, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'healthStartPeriodUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'noHealthCheck': new FormControl({ value: dockerService.noHealthCheck, disabled:  this.isDisabled() }),
      'updateDelay': new FormControl({ value: dockerService.updateDelay, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'updateDelayUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'updateFailureAction': new FormControl({ value: dockerService.updateFailureAction, disabled:  this.isDisabled() }),
      'updateMaxFailureRatio': new FormControl({ value: dockerService.updateMaxFailureRatio, disabled:  this.isDisabled() },
        [Validators.min(0), Validators.max(1)]),
      'updateMonitor': new FormControl({ value: dockerService.updateMonitor, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'updateMonitorUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'updateOrder': new FormControl({ value: dockerService.updateOrder, disabled:  this.isDisabled() }),
      'updateParallelism': new FormControl({ value: dockerService.updateParallelism, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'rollbackDelay': new FormControl({ value: dockerService.rollbackDelay, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'rollbackDelayUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'rollbackFailureAction': new FormControl({ value: dockerService.rollbackFailureAction, disabled:  this.isDisabled() }),
      'rollbackMaxFailureRatio': new FormControl({ value: dockerService.rollbackMaxFailureRatio, disabled:  this.isDisabled() },
        [Validators.min(0), Validators.max(1)]),
      'rollbackMonitor': new FormControl({ value: dockerService.rollbackMonitor, disabled:  this.isDisabled() }, [Validators.min(0)]),
      'rollbackMonitorUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'rollbackOrder': new FormControl({ value: dockerService.rollbackOrder, disabled:  this.isDisabled() }),
      'rollbackParallelism': new FormControl({ value: dockerService.rollbackParallelism, disabled:  this.isDisabled() },
        [Validators.min(0)]),
      'reserveMemory': new FormControl({ value: dockerService.reserveMemory, disabled:  this.isDisabled() }),
      'reserveMemoryUnit': new FormControl({ value: 'Bytes', disabled:  this.isDisabled() }),
      'reserveCpu': new FormControl({ value: dockerService.reserveCpu, disabled:  this.isDisabled() }),
      'limitMemory': new FormControl({ value: dockerService.limitMemory, disabled:  this.isDisabled() }),
      'limitMemoryUnit': new FormControl({ value: 'Bytes', disabled:  this.isDisabled() }),
      'limitCpu': new FormControl({ value: dockerService.limitCpu, disabled:  this.isDisabled() }),
      'dnsServers': new FormArray([]),
      'dnsOptions': new FormArray([]),
      'dnsSearches': new FormArray([]),
      'args': new FormArray([]),
      'workDir': new FormControl({ value: dockerService.workDir, disabled:  this.isDisabled() }),
      'entrypoint': new FormControl({ value: dockerService.entrypoint, disabled:  this.isDisabled() }),
      'user': new FormControl({ value: dockerService.user, disabled:  this.isDisabled() }),
      'groups': new FormArray([]),
      'stopSignal': new FormControl({ value: dockerService.stopSignal, disabled:  this.isDisabled() }),
      'stopGracePeriod': new FormControl({ value: dockerService.stopGracePeriod, disabled:  this.isDisabled() }),
      'stopGracePeriodUnit': new FormControl({ value: 'ns', disabled:  this.isDisabled() }),
      'readOnly': new FormControl({ value: dockerService.readOnly, disabled:  this.isDisabled() }),
      'logDriver': new FormControl({ value: dockerService.logDriver, disabled:  this.isDisabled() }),
      'logOptions': new FormArray([]),
      'mounts': new FormArray([]),
      'configs': new FormArray([]),
      'secrets': new FormArray([]),
    });
    this.addConfigsOrSecrets(dockerService, true);
    this.addConfigsOrSecrets(dockerService, false);
    this.addConstraints(dockerService);
    this.addMounts(dockerService);
    this.addEnv(dockerService);
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'labels', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'containerLabels', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'placementPreferences', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'dnsServers', true, this.isDisabled(), 'dnsServer');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'dnsOptions', true, this.isDisabled(), 'dnsOption');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'dnsSearches', true, this.isDisabled(), 'dnsSearch');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'args', true, this.isDisabled(), 'arg');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'groups', true, this.isDisabled(), 'group');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'logOptions', true, this.isDisabled(), 'option', 'value');

    PortsFormUtil.initPortsForm(this.serviceForm, dockerService.ports, this.isDisabled());
    NetworkFormUtil.initNetworkForm(this.serviceForm, this.formsService, dockerService, this.isDisabled());
  }

  private addConstraints(dockerService: DockerService) {
    if (dockerService && dockerService.constraints) {
      for (const constraint of dockerService.constraints) {
        this.addConstraint(this.serviceForm, constraint, this.isDisabled());
      }
    }
    this.addConstraint(this.serviceForm, null, this.isDisabled());
  }

  private addConstraint(formGroup: FormGroup, object?: any, isDisabled?: boolean) {
    const name = this.formsService.getValue(object, 'name');
    const value = this.formsService.getValue(object, 'value');
    const different = this.formsService.getValue(object, 'different');
    const constraint = new FormGroup({
      'name': new FormControl({ value: name, disabled: isDisabled }),
      'value': new FormControl({ value: value, disabled: isDisabled }),
      'different': new FormControl({ value: different ? '!=' : '==', disabled: isDisabled }),
    });
    (<FormArray>formGroup.get('constraints')).push(constraint);
  }

  private addConfigsOrSecrets(dockerService: DockerService, isConfigs: boolean) {
    let field = 'configs';
    if (!isConfigs) {
      field = 'secrets';
    }
    if (dockerService[field] && dockerService[field].length > 0) {
      for (const object of dockerService[field]) {
        this.addConfigOrSecret(this.serviceForm, isConfigs, object, this.isDisabled());
      }
    }
    this.addConfigOrSecret(this.serviceForm, isConfigs, new DockerServiceSecretAndConfig(), this.isDisabled());
  }

  private addConfigOrSecret(formGroup: FormGroup, isConfigs: boolean, object?: DockerServiceSecretAndConfig, isDisabled?: boolean) {
    let field = 'configs';
    if (!isConfigs) {
      field = 'secrets';
    }

    const fileMode = this.formsService.getValue(object, 'fileMode');
    const group = new FormGroup({
      'name': new FormControl({ value: this.formsService.getValue(object, 'name'), disabled: isDisabled }),
      'fileName': new FormControl({ value: this.formsService.getValue(object, 'fileName'), disabled: isDisabled }),
      'fileUID': new FormControl({ value: this.formsService.getValue(object, 'fileUID'), disabled: isDisabled }),
      'fileGID': new FormControl({ value: this.formsService.getValue(object, 'fileGID'), disabled: isDisabled }),
      'fileMode': new FormControl({ value: '0' + parseInt(fileMode, 10).toString(8), disabled: isDisabled })
    });
    (<FormArray>formGroup.get(field)).push(group);
  }

  private addMounts(dockerService: DockerService) {
    if (dockerService.mounts && dockerService.mounts.length > 0) {
      for (const mount of dockerService.mounts) {
        this.addMount(this.serviceForm, mount, this.isDisabled());
      }
    }
    this.addMount(this.serviceForm, new DockerServiceMount(), this.isDisabled());
  }

  addMount(formGroup: FormGroup, mount?: DockerServiceMount, isDisabled?: boolean): void {
    const mountConfig = new FormGroup({
      'type': new FormControl({ value: this.formsService.getValue(mount, 'type'), disabled: isDisabled }),
      'source': new FormControl({ value: this.formsService.getValue(mount, 'source'), disabled: isDisabled }),
      'destination': new FormControl({ value: this.formsService.getValue(mount, 'destination'), disabled: isDisabled }),
      'readOnly': new FormControl({ value: this.formsService.getValue(mount, 'readOnly'), disabled: isDisabled }),
      'consistency': new FormControl({ value: this.formsService.getValue(mount, 'consistency'), disabled: isDisabled })
    });

    let bindOption = new BindMountOptions();
    if (mount && mount.bindOptions) {
      bindOption = mount.bindOptions;
    }

    let tmpfsOptions = new DockerTmpfsMountOptions();
    if (mount && mount.tmpfsOptions) {
      tmpfsOptions = mount.tmpfsOptions;
    }

    let volumeOptions = new DockerVolumeMountOptions();
    if (mount && mount.volumeOptions) {
      volumeOptions = mount.volumeOptions;
    }

    mountConfig.addControl('propagation', new FormControl({ value: this.formsService.getValue(bindOption,
      'propagation', 'rprivate'), disabled: isDisabled }));
    mountConfig.addControl('size', new FormControl({ value: this.formsService.getValue(tmpfsOptions,
      'size'), disabled: isDisabled }));
    mountConfig.addControl('sizeUnit', new FormControl({ value: 'Bytes', disabled: isDisabled }));
    mountConfig.addControl('mode', new FormControl({ value: this.formsService.getValue(tmpfsOptions, 'mode'), disabled: isDisabled }));
    mountConfig.addControl('driver', new FormControl({ value: this.formsService.getValue(volumeOptions, 'driver', 'local'),
      disabled: isDisabled }));
    mountConfig.addControl('noCopy', new FormControl({ value: this.formsService.getValue(volumeOptions, 'noCopy', 'true'),
      disabled: isDisabled }));

    mountConfig.addControl('options', new FormArray([]));
    this.formsService.parseObjectFieldToOptions(mountConfig, volumeOptions, 'options', true, this.isDisabled(), 'option');
    mountConfig.addControl('labels', new FormArray([]));
    this.formsService.parseObjectFieldToOptions(mountConfig, volumeOptions, 'labels', true, this.isDisabled(), 'name', 'value');
    (<FormArray>formGroup.get('mounts')).push(mountConfig);
  }

  private addEnv(dockerService: DockerService) {
    if (dockerService.env && dockerService.env.length > 0) {
      for (const env of dockerService.env) {
        console.log(env);
        const envArr = env.split('=');
        if (envArr.length === 2) {
          console.log(envArr);
          this.formsService.addOption(this.serviceForm, 'env', this.isDisabled(), 'name', 'value', envArr[0], envArr[1]);
        } else if (env.startsWith('=')) {
          this.formsService.addOption(this.serviceForm, 'env', this.isDisabled(), 'name', 'value', '', env.substr(1));
        } else if (env.endsWith('=')) {
          this.formsService.addOption(this.serviceForm, 'env', this.isDisabled(), 'name', 'value', env.substr(0, env.length - 1), '');
        }
      }
    }
    this.formsService.addOption(this.serviceForm, 'env', this.isDisabled(), 'name', 'value');
  }

  disableForm(): void {
    this.serviceForm.disable();
  }

  enableForm(): void {
    this.serviceForm.enable();
    this.serviceForm.get('name').disable();
    this.serviceForm.get('global').disable();
    if (this.isGlobalService) {
      this.serviceForm.get('replicas').disable();
    }
  }

  toggleGlobal(): void {
    if (!this.serviceForm.get('global').disabled) {
      this.isGlobalService = !this.isGlobalService;
      if (this.isGlobalService) {
        this.serviceForm.get('replicas').disable();
      } else {
        this.serviceForm.get('replicas').enable();
      }
    }
  }

  getTimeUnits(): Array<string> {
    return Object.keys(this.formsService.timeUnits);
  }

  getMemoryUnits(): Array<string> {
    return Object.keys(this.formsService.memoryUnits);
  }

  changeTimeUnit(event, field: string): void {
    const currentValue = this.serviceForm.get(field).value;
    if (isNumber(currentValue)) {
      const newValue = this.formsService.calculateTimeValue(currentValue, this.previousTimeFields[field + 'Unit'], event.value);
      this.serviceForm.get(field).setValue(newValue);
    }
    this.previousTimeFields[field + 'Unit'] = event.value;
  }

  changeMemoryUnit(event, field: string, group?: FormGroup): void {
    if (!group) {
      group = this.serviceForm;
    }

    const currentValue = group.get(field).value;
    if (isNumber(currentValue)) {
      const newValue = this.formsService.calculateMemoryValue(currentValue, this.previousMemoryFields[ field + 'Unit' ], event.value);
      group.get(field).setValue(newValue);
    }
    this.previousMemoryFields[ field + 'Unit' ] = event.value;
  }

  getNewDockerService(values): DockerService {
    const dockerService = new DockerService();
    if (values['name']) {
      dockerService.name = values['name'];
    } else {
      dockerService.name = this.serviceName;
    }
    dockerService.image = values['image'];
    dockerService.replicas = values['replicas'];
    dockerService.global = values['global'];

    PortsFormUtil.addPorts(this.serviceForm, dockerService);
    NetworkFormUtil.addNetworks(this.serviceForm, this.formsService, dockerService);

    const envValues = values['env'];
    const env = [];
    for (const envValue of envValues) {
      const name = envValue['name'];
      const value = envValue['value'];
      if (name !== '' || value !== '') {
        env.push(name + '=' + value);
      }
    }
    dockerService.env = env;

    this.formsService.parseOptionsToObjectField(dockerService, values, 'labels', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'containerLabels', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'placementPreferences', 'name', 'value');

    dockerService.restartCondition = values['restartCondition'];
    dockerService.restartMaxAttempts = values['restartMaxAttempts'];
    dockerService.restartDelay = this.formsService.calculateTimeValue(values['restartDelay'],
      this.previousTimeFields['restartDelayUnit'], 'ns');
    dockerService.restartWindow = this.formsService.calculateTimeValue(values['restartWindow'],
      this.previousTimeFields['restartWindowUnit'], 'ns');

    dockerService.healthCmd = values['healthCmd'];
    dockerService.healthInterval = this.formsService.calculateTimeValue(values['healthInterval'],
      this.previousTimeFields['healthIntervalUnit'], 'ns');
    dockerService.healthTimeout = this.formsService.calculateTimeValue(values['healthTimeout'],
      this.previousTimeFields['healthTimeoutUnit'], 'ns');
    dockerService.healthStartPeriod = this.formsService.calculateTimeValue(values['healthStartPeriod'],
      this.previousTimeFields['healthStartPeriodUnit'], 'ns');
    dockerService.healthRetries = values['healthRetries'];
    dockerService.noHealthCheck = values['noHealthCheck'];

    dockerService.updateDelay = this.formsService.calculateTimeValue(values['updateDelay'],
      this.previousTimeFields['updateDelayUnit'], 'ns');
    dockerService.updateFailureAction = values['updateFailureAction'];
    dockerService.updateMaxFailureRatio = values['updateMaxFailureRatio'];
    dockerService.updateMonitor = this.formsService.calculateTimeValue(values['updateMonitor'],
      this.previousTimeFields['updateMonitorUnit'], 'ns');
    dockerService.updateOrder = values['updateOrder'];
    dockerService.updateParallelism = values['updateParallelism'];

    dockerService.rollbackDelay = this.formsService.calculateTimeValue(values['rollbackDelay'],
      this.previousTimeFields['rollbackDelayUnit'], 'ns');
    dockerService.rollbackFailureAction = values['rollbackFailureAction'];
    dockerService.rollbackMaxFailureRatio = values['rollbackMaxFailureRatio'];
    dockerService.rollbackMonitor = this.formsService.calculateTimeValue(values['rollbackMonitor'],
      this.previousTimeFields['rollbackMonitorUnit'], 'ns');
    dockerService.rollbackOrder = values['rollbackOrder'];
    dockerService.rollbackParallelism = values['rollbackParallelism'];

    dockerService.reserveMemory = this.formsService.calculateMemoryValue(values['reserveMemory'],
      this.previousMemoryFields['reserveMemoryUnit'], 'Bytes');
    dockerService.reserveCpu = values['reserveCpu'];

    dockerService.limitMemory = this.formsService.calculateMemoryValue(values['limitMemory'],
      this.previousMemoryFields['limitMemoryUnit'], 'Bytes');
    dockerService.limitCpu = values['limitCpu'];

    this.formsService.parseOptionsToObjectField(dockerService, values, 'dnsServers', 'dnsServer');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'dnsOptions', 'dnsOption');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'dnsSearches', 'dnsSearch');

    this.formsService.parseOptionsToObjectField(dockerService, values, 'args', 'arg');
    dockerService.workDir = values['workDir'];
    dockerService.entrypoint = values['entrypoint'];
    dockerService.user = values['user'];
    this.formsService.parseOptionsToObjectField(dockerService, values, 'groups', 'group');
    dockerService.stopSignal = values['stopSignal'];
    dockerService.stopGracePeriod = this.formsService.calculateTimeValue(values['stopGracePeriod'],
      this.previousTimeFields['stopGracePeriodUnit'], 'ns');
    dockerService.readOnly = values['readOnly'];

    dockerService.logDriver = values['logDriver'];
    this.formsService.parseOptionsToObjectField(dockerService, values, 'logOptions', 'option', 'value');

    this.parseMounts(dockerService, values);
    this.parseConfigsOrSecrets(dockerService, values, true);
    this.parseConfigsOrSecrets(dockerService, values, false);
    this.parseConstraints(dockerService, values);
    console.log(dockerService);
    return dockerService;
  }

  parseConstraints(dockerService: DockerService, values) {
    const constraintsArr = values['constraints'];
    const constraints = [];
    for (const constraintObj of constraintsArr) {
      if (constraintObj.name.trim() !== '' && constraintObj.value.trim() !== '') {
        const constraint: any = {
          name: constraintObj.name,
          value: constraintObj.value
        };
        constraint.different = (constraintObj.different === '!=');
        constraints.push(constraint);
      }
    }
    dockerService.constraints = constraints;
  }

  parseConfigsOrSecrets(dockerService: DockerService, values, isConfigs: boolean) {
    let field = 'configs';
    if (!isConfigs) {
      field = 'secrets';
    }
    const objectsArr = values[field];
    const objects = [];
    for (const object of objectsArr) {
      if (object['name'] !== '') {
        const dockerObject = new DockerServiceSecretAndConfig();
        dockerObject.id = this.getConfigOrSecretId(object['name'], isConfigs);
        dockerObject.name = object['name'];
        dockerObject.fileName = object['fileName'];
        dockerObject.fileMode = parseInt(object['fileMode'], 8);
        dockerObject.fileGID = object['fileGID'];
        dockerObject.fileUID = object['fileUID'];
        objects.push(dockerObject);
      }

    }
    dockerService[field] = objects;
  }

  getConfigOrSecretId(name: string, isConfigs: boolean): string {
      let arr = this.configs;
      if (!isConfigs) {
        arr = this.secrets;
      }
      for (const obj of arr) {
        if (name === obj.name) {
          return obj.id;
        }
      }
      return null;
  }

  parseMounts(dockerService: DockerService, values) {
    const mountsArr = values['mounts'];
    const mounts = [];
    for (const formMount of mountsArr) {
      if (formMount['type'] !== '') {
        const mount = new DockerServiceMount();
        mount.type = formMount['type'];
        mount.source = formMount['source'];
        mount.destination = formMount['destination'];
        mount.readOnly = formMount['readOnly'];
        mount.consistency = formMount['consistency'];

        if (mount.type === 'bind') {
          const bindOptions = new BindMountOptions();
          bindOptions.propagation = formMount['propagation'];
          mount.bindOptions = bindOptions;
        } else if (mount.type === 'tmpfs') {
          const tmpfsOptions = new DockerTmpfsMountOptions();
          tmpfsOptions.mode = formMount['mode'];
          tmpfsOptions.size = this.formsService.calculateMemoryValue(formMount['size'],
            this.previousMemoryFields['sizeUnit'], 'Bytes');
          mount.tmpfsOptions = tmpfsOptions;
        } else if (mount.type === 'volume') {
          const volumeOptions = new DockerVolumeMountOptions();
          volumeOptions.noCopy = formMount['noCopy'];
          volumeOptions.driver = formMount['driver'];
          this.formsService.parseOptionsToObjectField(volumeOptions, formMount, 'options', 'option');
          this.formsService.parseOptionsToObjectField(volumeOptions, formMount, 'labels', 'name', 'value');
          mount.volumeOptions = volumeOptions;
        }

        mounts.push(mount);
      }
    }
    dockerService.mounts = mounts;
  }

  setEditMode(): void {
    this.editMode = true;
    this.enableForm();
  }

  forceUpdate(): void {
    const dockerService = new DockerService();
    dockerService.name = this.serviceName;
    dockerService.forceUpdate = true;
    this.subscriptions.push(this.dockerServicesService.updateService(dockerService).subscribe());
  }

  cancelEdit(): void {
    this.editMode = false;
    this.disableForm();
    this.initCreateForm(this.service);
  }

  openSaveDialog(): void {
    if (this.serviceForm.valid) {
      this.formInvalid = false;
      const dialogRef = this.dialog.open(ManageServiceConfirmation, {
        width: '20rem',
        data: {
          title: 'Save service changes',
          registryToggle: 'Change Registry'
        }
      });

      this.subscriptions.push(dialogRef.afterClosed().subscribe(confirmation => {
        if (confirmation.result === true) {
          const dockerService = this.getNewDockerService(this.serviceForm.value);
          if (confirmation['privateRegistry']) {
            dockerService.registryUsername = confirmation.username;
            dockerService.registryPassword = btoa(confirmation.password);
            dockerService.dockerHubRegistry = confirmation.dockerHubRegistry;
          }
          this.subscriptions.push(this.dockerServicesService.updateService(dockerService).subscribe(
            () => {
              this.editMode = false;
              this.serviceForm.markAsUntouched();
              this.disableForm();
              this.loadService(true);
          }));
        }
      }));
    } else if (this.serviceForm.valid && !this.serviceForm.touched) {
      this.editMode = false;
      this.disableForm();
    } else {
      this.formInvalid = true;
    }
  }

  openRollbackDialog(): void {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        width: '20rem',
        data: {
          title: 'Rollback to previous version',
        }
      });

      this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
        if (result === true) {
          const dockerService = new DockerService();
          dockerService.name = this.serviceForm.get('name').value;
          dockerService.rollback = true;
          this.subscriptions.push(this.dockerServicesService.updateService(dockerService).subscribe(
            () => {
              this.loadService(true);
            }
          ));
        }
      }));
  }

  openRemoveDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: 'Remove service'
      }
    });

    this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.dockerServicesService.removeService(this.serviceForm.get('name').value)
          .subscribe(() => {
            this.router.navigate(['/services']);
        });
      }
    }));
  }


  exportService(): void {
    const cleanedDockerService = this.getCleanedObject(new Set([
      'id',
      'createdAt',
      'updatedAt'
    ]), false, this.service);
    const serviceAsString = JSON.stringify(cleanedDockerService, null, 2);
    const element = document.createElement('a');
    element.setAttribute('href', 'data:application/json;charset=utf-8,' + encodeURIComponent(serviceAsString));
    element.setAttribute('download', this.service.name + '.json');
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  }

  loadJsonService(event: any): void {
      try {
        const dockerService = JSON.parse(event);
        this.initCreateForm(dockerService);
        this.snackbarService.showSuccess('Loaded service from file');
      } catch (err) {
        this.snackbarService.showError('Invalid services file');
      }
  }

   getCleanedObject(fields: Set<string>, include: boolean, object: any) {
    const newObj = {};
    for (const field in object) {
      if (object.hasOwnProperty(field)) {
        let add = !fields.has(field);
        if (include) {
          add = fields.has(field);
        }
        if (add) {
          newObj[field] = object[field];
        }
      }
    }
    return newObj;
  }
}
