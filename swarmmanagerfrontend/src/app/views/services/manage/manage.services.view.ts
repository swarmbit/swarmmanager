import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { BaseView } from '../../base.view';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../../../components/confirmation.dialog/confirmation.dialog.component';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { DockerService } from '../../../services/docker/services/docker.service';
import { CleanServiceImagePipe } from '../../../pipes/clean.service.image.pipe';
import { HttpErrorResponse } from '@angular/common/http';
import { DockerServicePort } from '../../../services/docker/services/docker.service.port';
import { FormsService } from '../../../services/utils/forms.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { DockerNetworkSummary } from '../../../services/docker/networks/docker.network.summary';
import { ManageServiceConfirmation } from './manage.service.confirmation';
import { isNumber } from 'util';
import {
  BindMountOptions, DockerServiceMount,
  DockerTmpfsMountOptions, DockerVolumeMountOptions
} from '../../../services/docker/services/docker.service.mount';
import { SnackbarService } from '../../../services/snackbar/snackbar.service';
import { BrowserService } from '../../../services/utils/browser.service';

@Component({
  selector: 'app-manage-service',
  styleUrls: ['manage.services.view.scss'],
  templateUrl: 'manage.services.view.html'
})
export class ManageServicesView extends BaseView implements OnInit {

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  editMode: boolean;
  serviceForm: FormGroup;
  serviceName: string;
  isGlobalService: boolean;
  networks: DockerNetworkSummary[] = [];
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

  @ViewChild('importInput')
  importInput: ElementRef;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private dockerServicesService: DockerServicesService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog,
              public formsService: FormsService,
              private networkService: DockerNetworksService,
              private snackbarService: SnackbarService,
              private browserService: BrowserService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    super.enableBackArrow();
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    this.loadFunction = this.loadService;
  }

  ngOnInit(): void {
    this.initCreateForm();
    this.networkService.getNetworksList(true).subscribe(
      networks => {
        for (const network of networks) {
          if (network.name != 'ingress') {
            this.networks.push(network);
          }
        }
      }
    );
  }

  loadService() {
    if (this.isDetails && this.serviceName) {
      this.dockerServicesService.getService(this.serviceName)
        .subscribe(
          (dockerService: DockerService) => {
            this.initCreateForm(dockerService);
          },
          (err: HttpErrorResponse) => {
            this.goBack(this.router, 'services');
          });
    }
  }

  initCreateForm(dockerService ?: DockerService): void {
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

  getDockerServiceWithDefaults(): DockerService {
    const dockerService = new DockerService();
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
      'ports': new FormArray([]),
      'env': new FormArray([]),
      'networks':  new FormArray([]),
      'hostname': new FormControl({ value: dockerService.hostname, disabled:  this.isDisabled() }),
      'endpointMode': new FormControl({ value: dockerService.endpointMode, disabled:  this.isDisabled() }),
      'hosts': new FormArray([]),
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
      'mounts': new FormArray([])
    });
    this.addMounts(dockerService);
    this.addPorts(dockerService);
    this.addEnv(dockerService);
    this.addNetworks(dockerService);
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'labels', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'containerLabels', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'constraints', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'placementPreferences', true, this.isDisabled(), 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.serviceForm, dockerService, 'hosts', true, this.isDisabled(), 'host');
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
        const envArr = env.split('=');
        if (envArr.length == 2) {
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

  private addPorts(dockerService: DockerService) {
    if (dockerService.ports && dockerService.ports.length > 0) {
      for (const port of dockerService.ports) {
        this.addPort(port, this.isDisabled());
      }
    }
    this.addPort(new DockerServicePort(), this.isDisabled());
  }

  addPort(port: DockerServicePort, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['protocol'] = new FormControl({value: port && port.protocol ? port.protocol : '', disabled: disabled});
    formGroupObj['published'] = new FormControl({value: port && port.published ? port.published : '', disabled: disabled});
    formGroupObj['target'] = new FormControl({value: port && port.target ? port.target : '', disabled: disabled});
    (<FormArray>this.serviceForm.get('ports')).push(new FormGroup(formGroupObj));
  }

  private addNetworks(dockerService: DockerService) {
    if (dockerService.networks && dockerService.networks.length > 0) {
      for (const network of dockerService.networks) {
        this.addNetwork(network, this.isDisabled());
      }
    }
    this.addNetwork('', this.isDisabled());
  }

  addNetwork(network: string, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['id'] = new FormControl({value: network ? network : '', disabled: disabled});
    (<FormArray>this.serviceForm.get('networks')).push(new FormGroup(formGroupObj));
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
    const portsValues = values['ports'];
    const ports = [];
    for (const portValue of portsValues) {
      const protocol = portValue['protocol'];
      const target = portValue['target'];
      const published = portValue['published'];
      if (protocol !== '' || target !== '' || published !== '') {
        const port = new DockerServicePort();
        port.protocol = protocol;
        port.target = target;
        port.published = published;
        ports.push(port);
      }
    }
    dockerService.ports = ports;
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

    const networksValues = values['networks'];
    const networks = [];
    for (const networkValue of networksValues) {
      const id = networkValue['id'];
      if (id !== '') {
        networks.push(id);
      }
    }
    dockerService.networks = networks;

    this.formsService.parseOptionsToObjectField(dockerService, values, 'labels', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'containerLabels', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'constraints', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'placementPreferences', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerService, values, 'hosts', 'host');

    dockerService.hostname = values['hostname'];
    dockerService.endpointMode = values['endpointMode'];
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
    return dockerService;
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
              this.loadService();
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
              this.loadService();
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

  importService(): void {
    this.importInput.nativeElement.click();
  }

  loadJsonService(event: any): void {
    const files = event.target.files;
    if (files && files.length > 0) {
      const serviceJsonFile = files[ 0 ];
      const fr = new FileReader();
      fr.onload = (e: any) => {
        const lines = e.target.result;
        try {
          const dockerService = JSON.parse(lines);
          this.initCreateForm(dockerService);
          this.snackbarService.showSuccess('Loaded service from file');
        } catch (err) {
          this.snackbarService.showError('Invalid services file')
        }
      };
      fr.readAsText(serviceJsonFile);
    }
  }

  getCleanedObject(fields: Set<string>, include: boolean, object: any) {
    const newObj = {};
    for (const field in object) {
      let add = !fields.has(field);
      if (include) {
        add = fields.has(field);
      }
      if (add) {
        newObj[field] = object[field];
      }
    }
    return newObj;
  }

}
