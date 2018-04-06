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

@Component({
  selector: 'app-manage-network',
  templateUrl: 'manage.network.view.html'
})
export class ManageNetworkView extends BaseView {

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  networkForm: FormGroup;
  networkName: string;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private networksService: DockerNetworksService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog,
              public formsService: FormsService,
              public ngZone: NgZone
  ) {
    super(headerService, route, swarmService, userService);
    super.enableBackArrow('/networks');
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    this.loadFunction = this.loadNetworks;
    this.initCreateForm();
  }

  loadNetworks() {
    this.networksService.getNetwork(this.networkName)
      .subscribe(
        (dockerNetwork: any) => {
          this.initCreateForm(dockerNetwork);
        },
        (err: HttpErrorResponse) => {
          this.router.navigate(['/networks']);
        });
  }

  initCreateForm(dockerNetwork ?: DockerNetwork): void {
    if (!dockerNetwork && this.isDetails) {
      this.subscriptions.push(this.route.data
        .subscribe(
          (data: Data) => {
            const dockerNetworkData = data['dockerNetwork'];
            this.networkName = dockerNetworkData.name;
            super.setViewName('Network ' + dockerNetworkData.name);
            this.createForm(dockerNetworkData);
          }
        ));
    } else if (dockerNetwork) {
      this.createForm(dockerNetwork);
    } else {
      this.createForm(new DockerNetwork());
    }
  }

  createNetwork(): void {
    if (this.networkForm.valid) {
      this.formInvalid = false;
      this.subscriptions.push(this.networksService.createNetwork(this.getNewDockerNetwork(this.networkForm.value)).subscribe(
        () => {
          this.router.navigate(['/networks']);
        }
      ));
    } else {
      this.formInvalid = true;
    }
  }

  createForm(dockerNetwork: DockerNetwork): void {
    let driver = 'overlay';
    if (dockerNetwork.driver && dockerNetwork.driver != '') {
      driver = dockerNetwork.driver;
    }
    let ipamDriver = 'default';
    if (dockerNetwork.ipamDriver && dockerNetwork.ipamDriver != '') {
      ipamDriver = dockerNetwork.ipamDriver;
    }

    this.networkForm = new FormGroup({
        'name': new FormControl({ value: dockerNetwork.name, disabled: this.isDetails }, [Validators.required]),
        'driver': new FormControl({ value: driver, disabled: this.isDetails }),
        'ipamDriver': new FormControl({ value: ipamDriver, disabled: this.isDetails }),
        'ipv6':  new FormControl({ value: dockerNetwork.ipv6, disabled: this.isDetails }),
        'internal': new FormControl({ value: dockerNetwork.internal, disabled: this.isDetails }),
        'attachable': new FormControl({ value: dockerNetwork.attachable, disabled: this.isDetails }),
        'labels': new FormArray([]),
        'ipamOptions': new FormArray([]),
        'options': new FormArray([]),
        'ipamConfigs': new FormArray([])
    });
    this.formsService
      .parseObjectFieldToOptions(this.networkForm, dockerNetwork, 'labels', !this.isDetails, this.isDetails, 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.networkForm, dockerNetwork, 'ipamOptions', !this.isDetails, this.isDetails, 'name', 'value');
    this.formsService
      .parseObjectFieldToOptions(this.networkForm, dockerNetwork, 'options', !this.isDetails, this.isDetails, 'name', 'value');
    if (dockerNetwork.ipamConfigs) {
      for (const ipamConfig of dockerNetwork.ipamConfigs) {
        this.addIpamConfig(this.networkForm, ipamConfig);
      }
    } else if (!this.isDetails) {
      this.addIpamConfig(this.networkForm);
    }
  }

  addIpamConfig(formGroup: FormGroup, ipamConfig?: DockerIpamConfig): void {
    const ipamConfigGroup = new FormGroup({
      'subnet': new FormControl({ value: this.formsService.getValue(ipamConfig, 'subnet'), disabled: this.isDetails }),
      'ipRange': new FormControl({ value: this.formsService.getValue(ipamConfig, 'ipRange'), disabled: this.isDetails }),
      'gateway': new FormControl({ value: this.formsService.getValue(ipamConfig, 'gateway'), disabled: this.isDetails }),
      'auxAddress':   new FormArray([])
    });
    this.formsService
      .parseObjectFieldToOptions(ipamConfigGroup, ipamConfig, 'auxAddress', !this.isDetails, this.isDetails, 'deviceName', 'ipAddress');
    (<FormArray>formGroup.get('ipamConfigs')).push(ipamConfigGroup);
  }

  getNewDockerNetwork(values): DockerNetwork {
    const dockerNetwork = new DockerNetwork();
    dockerNetwork.name = values['name'];
    dockerNetwork.driver = values['driver'];
    dockerNetwork.ipamDriver = values['ipamDriver'];
    dockerNetwork.ipv6 = values['ipv6'];
    dockerNetwork.internal = values['internal'];
    dockerNetwork.attachable = values['attachable'];
    this.formsService.parseOptionsToObjectField(dockerNetwork, values, 'labels', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerNetwork, values, 'options', 'name', 'value');
    this.formsService.parseOptionsToObjectField(dockerNetwork, values, 'ipamOptions', 'name', 'value');
    const ipamConfigs: DockerIpamConfig[] = [];
    for (const ipamConfigValues of values['ipamConfigs']) {
      const ipamConfig = this.parseToIpamConfig(ipamConfigValues);
      if (ipamConfig != null) {
        ipamConfigs.push(ipamConfig);
      }
    }
    if (ipamConfigs.length > 0) {
      dockerNetwork.ipamConfigs = ipamConfigs;
    }
    return dockerNetwork;
  }

  parseToIpamConfig(values): DockerIpamConfig {
    const subnet = values['subnet'];
    const ipRange = values['ipRange'];
    const gateway = values['gateway'];
    if (subnet && subnet != '' || ipRange && ipRange != '' || gateway && gateway != '') {
      const ipamConfig = new DockerIpamConfig();
      ipamConfig.subnet = subnet;
      ipamConfig.ipRange = ipRange;
      ipamConfig.gateway = gateway;
      this.formsService.parseOptionsToObjectField(ipamConfig, values, 'auxAddress', 'deviceName', 'ipAddress');
      return ipamConfig;
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: 'Remove Network'
      }
    });

    this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.networksService.removeNetwork(this.networkForm.get('name').value)
          .subscribe(() => {
            this.router.navigate(['/networks']);
        });
      }
    }));
  }

}
