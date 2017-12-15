import { Component } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BaseView } from '../../base.view';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { DockerIpamConfig } from '../../../services/docker/networks/docker.ipam.config';
import { Router } from '@angular/router';
import { DISABLED } from '@angular/forms/src/model';

@Component({
  selector: 'app-manage-network',
  styleUrls: ['manage.network.view.scss'],
  templateUrl: 'manage.network.view.html'
})
export class ManageNetworkView extends BaseView {

  createNetworkForm: FormGroup;

  name: AbstractControl;
  driver: AbstractControl;
  ipamDriver: AbstractControl;
  subnet1: AbstractControl;
  ipRange1: AbstractControl;
  gateway1: AbstractControl;
  deviceName1: AbstractControl;
  ipAddress1: AbstractControl;
  ipamOption1: AbstractControl;
  ipamOptionValue1: AbstractControl;
  label1: AbstractControl;
  labelValue1: AbstractControl;
  option1: AbstractControl;
  optionValue1: AbstractControl;
  ipv6: AbstractControl;
  internal: AbstractControl;
  attachable: AbstractControl;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private networksService: DockerNetworksService,
              private router: Router,
              formBuilder: FormBuilder
  ) {
    super(headerService, 'Create Network', swarmService, userService);
    super.enableBackArrow('/networks');
    this.createNetworkForm = formBuilder.group({
      'name':  ['', Validators.required],
      'driver':  [{ value: 'overlay', disabled: true }],
      'ipamDriver':  ['default'],
      'subnet1':  [''],
      'ipRange1':  [''],
      'gateway1':  [''],
      'deviceName1':  [''],
      'ipAddress1':  [''],
      'ipamOption1':  [''],
      'ipamOptionValue1':  [''],
      'label1':  [''],
      'labelValue1':  [''],
      'option1':  [''],
      'optionValue1':  [''],
      'ipv6':  [false],
      'internal':  [false],
      'attachable':  [false]
    });
    this.name = this.createNetworkForm.controls['name'];
    this.driver = this.createNetworkForm.controls['driver'];
    this.ipamDriver = this.createNetworkForm.controls['ipamDriver'];
    this.subnet1 = this.createNetworkForm.controls['subnet1'];
    this.ipRange1 = this.createNetworkForm.controls['ipRange1'];
    this.gateway1 = this.createNetworkForm.controls['gateway1'];
    this.deviceName1 = this.createNetworkForm.controls['deviceName1'];
    this.ipAddress1 = this.createNetworkForm.controls['ipAddress1'];
    this.ipamOption1 = this.createNetworkForm.controls['ipamOption1'];
    this.ipamOptionValue1 = this.createNetworkForm.controls['ipamOptionValue1'];
    this.label1 = this.createNetworkForm.controls['label1'];
    this.labelValue1 = this.createNetworkForm.controls['labelValue1'];
    this.option1 = this.createNetworkForm.controls['option1'];
    this.optionValue1 = this.createNetworkForm.controls['optionValue1'];
    this.ipv6 = this.createNetworkForm.controls['ipv6'];
    this.internal = this.createNetworkForm.controls['internal'];
    this.attachable = this.createNetworkForm.controls['attachable'];
  }

  createNetwork(): void {
    if (this.createNetworkForm.valid) {
      this.networksService.createNetwork(this.getNewDockerNetwork(this.createNetworkForm.value)).subscribe(
        () => {
          this.router.navigate(['/networks']);
        }
      );
    }
  }

  getNewDockerNetwork(values): DockerNetwork {
    const dockerNetwork = new DockerNetwork();
    dockerNetwork.name = values['name'];
    dockerNetwork.driver = values['drive'];
    dockerNetwork.ipamDriver = values['ipamDriver'];
    dockerNetwork.ipamConfigs = [];
    const ipamConfig = new DockerIpamConfig();
    ipamConfig.subnet = values['subnet1'];
    ipamConfig.ipRange = values['ipRange1'];
    ipamConfig.gateway = values['gateway1'];
    ipamConfig.auxAddress = {};
    ipamConfig.auxAddress[values['deviceName1']] = values['ipAddress1'];
    if (ipamConfig.subnet || ipamConfig.ipRange || ipamConfig.gateway || values['deviceName1'] || values['ipAddress1']) {
      dockerNetwork.ipamConfigs.push(ipamConfig);
    }
    dockerNetwork.ipamOptions = {};
    dockerNetwork.ipamOptions[values['ipamOption1']] = values['ipamOptionValue1'];
    dockerNetwork.labels = {};
    dockerNetwork.labels[values['label1']] = values['labelValue1'];
    dockerNetwork.options = {};
    dockerNetwork.options[values['option1']] = values['optionValue1'];
    dockerNetwork.ipv6 = values['ipv6'];
    dockerNetwork.internal = values['internal'];
    dockerNetwork.attachable = values['attachable'];
    return dockerNetwork;
  }

}

