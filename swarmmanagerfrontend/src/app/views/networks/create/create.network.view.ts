import { Component } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BaseView } from '../../base.view';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-network',
  styleUrls: ['create.network.view.scss'],
  templateUrl: 'create.network.view.html'
})
export class CreateNetworkView extends BaseView {

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
              formBuilder: FormBuilder
  ) {
    super(headerService, 'Create Network', swarmService, userService);
    super.enableBackArrow('/networks');
    this.createNetworkForm = formBuilder.group({
      'name':  ['', Validators.required],
      'driver':  ['overlay'],
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
      console.log(this.createNetworkForm.value);
    }
  }

}

