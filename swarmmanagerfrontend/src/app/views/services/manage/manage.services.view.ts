import { Component } from '@angular/core';
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

@Component({
  selector: 'app-manage-network',
  styleUrls: ['manage.services.view.scss'],
  templateUrl: 'manage.services.view.html'
})
export class ManageServicesView extends BaseView {

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  networkForm: FormGroup;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private networksService: DockerNetworksService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog
  ) {
    super(headerService, route, swarmService, userService);
    super.enableBackArrow('/networks');
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    if (this.isDetails) {
      this.subscriptions.push(this.route.data
        .subscribe(
          (data: Data) => {
            const dockerNetwork = data['dockerNetwork'];
            super.setViewName('Network ' + dockerNetwork.name);
            this.createForm(dockerNetwork);
          }
      ));
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
    this.parseNetworkFieldToOptions(this.networkForm, dockerNetwork, 'labels', 'name', 'value');
    this.parseNetworkFieldToOptions(this.networkForm, dockerNetwork, 'ipamOptions', 'name', 'value');
    this.parseNetworkFieldToOptions(this.networkForm, dockerNetwork, 'options', 'name', 'value');
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
      'subnet': new FormControl({ value: this.getIpamConfigValue(ipamConfig, 'subnet'), disabled: this.isDetails }),
      'ipRange': new FormControl({ value: this.getIpamConfigValue(ipamConfig, 'ipRange'), disabled: this.isDetails }),
      'gateway': new FormControl({ value: this.getIpamConfigValue(ipamConfig, 'gateway'), disabled: this.isDetails }),
      'auxAddress':   new FormArray([])
    });
    this.parseNetworkFieldToOptions(ipamConfigGroup, ipamConfig, 'auxAddress', 'deviceName', 'ipAddress');
    (<FormArray>formGroup.get('ipamConfigs')).push(ipamConfigGroup);
  }

  getIpamConfigValue(ipamConfig?: DockerIpamConfig, field?: string): string {
    let value = '';
    if (ipamConfig && field) {
      value = ipamConfig[field] ? ipamConfig[field]  : '';
    }
    return value;
  }

  removeIpamConfig(formGroup: FormGroup, index: number): void {
    (<FormArray>formGroup.get('ipamConfigs')).removeAt(index);
  }

  parseNetworkFieldToOptions(formGroup: FormGroup, object: any, field: string, firstLabel?: string, secondLabel?: string): void {
    if (object && object[field]) {
      for (const prop of Object.keys(object[field])) {
        this.addOption(formGroup, field, firstLabel, secondLabel, prop, object[field][prop]);
      }
    }
    if (!this.isDetails) {
      this.addOption(formGroup, field, firstLabel, secondLabel);
    }
  }

  addOption(formGroup: FormGroup, type: string, firstLabel: string, secondLabel: string, firstValue?: any, secondValue?: any): void {
    const formGroupObj = {};
    formGroupObj[firstLabel] = new FormControl({value: '', disabled: this.isDetails});
    formGroupObj[secondLabel] = new FormControl({value: '', disabled: this.isDetails});
    if (firstValue) {
      formGroupObj[firstLabel] = new FormControl({value: firstValue, disabled: this.isDetails});
      if (secondValue) {
        formGroupObj[secondLabel] = new FormControl({value: secondValue, disabled: this.isDetails});
      }
    }
    (<FormArray>formGroup.get(type)).push(new FormGroup(formGroupObj));
  }

  removeOption(formGroup: FormGroup, type: string, index: number): void {
    (<FormArray>formGroup.get(type)).removeAt(index);
  }

  getNewDockerNetwork(values): DockerNetwork {
    const dockerNetwork = new DockerNetwork();
    dockerNetwork.name = values['name'];
    dockerNetwork.driver = values['driver'];
    dockerNetwork.ipamDriver = values['ipamDriver'];
    dockerNetwork.ipv6 = values['ipv6'];
    dockerNetwork.internal = values['internal'];
    dockerNetwork.attachable = values['attachable'];
    this.parseOptionsToNetworkField(dockerNetwork, values, 'labels', 'name', 'value');
    this.parseOptionsToNetworkField(dockerNetwork, values, 'options', 'name', 'value');
    this.parseOptionsToNetworkField(dockerNetwork, values, 'ipamOptions', 'name', 'value');
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
      this.parseOptionsToNetworkField(ipamConfig, values, 'auxAddress', 'deviceName', 'ipAddress');
      return ipamConfig;
    }
  }

  parseOptionsToNetworkField(object: any, values: any, field: string, first: string, second: string): void {
    for (const value of values[field]) {
      if (value[first]) {
        if (!object[field]) {
          object[field] = {};
        }
        object[field][value[first]] = value[second];
      }
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
