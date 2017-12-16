import { Component, Inject } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { DockerNetworksService } from '../../../services/docker/networks/docker.networks.service';
import { BaseView } from '../../base.view';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DockerNetwork } from '../../../services/docker/networks/docker.network';
import { DockerIpamConfig } from '../../../services/docker/networks/docker.ipam.config';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material';
import { ConfirmationDialogComponent } from '../../../components/confirmation.dialog/confirmation.dialog.component';

@Component({
  selector: 'app-manage-network',
  styleUrls: ['manage.network.view.scss'],
  templateUrl: 'manage.network.view.html'
})
export class ManageNetworkView extends BaseView {

  isDetails: boolean;

  networkForm: FormGroup;

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
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              public dialog: MatDialog
  ) {
    super(headerService, route, swarmService, userService);
    super.enableBackArrow('/networks');
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    if (this.isDetails) {
      this.route.data
        .subscribe(
          (data: Data) => {
            const dockerNetwork = data['dockerNetwork'];
            super.setViewName('Network ' + dockerNetwork.name);
            this.createForm(dockerNetwork);
          }
        );
    } else {
      this.createForm(new DockerNetwork());
    }
  }

  createNetwork(): void {
    if (this.networkForm.valid) {
      this.networksService.createNetwork(this.getNewDockerNetwork(this.networkForm.value)).subscribe(
        () => {
          this.router.navigate(['/networks']);
        }
      );
    }
  }

  createForm(dockerNetwork: DockerNetwork): void {
    let driver = 'overlay';
    if (dockerNetwork.driver != '') {
      driver = dockerNetwork.driver;
    }
    let ipamDriver = 'default';
    if (dockerNetwork.ipamDriver != '') {
      ipamDriver = dockerNetwork.ipamDriver;
    }

    this.networkForm = this.formBuilder.group({
        'name':  [{ value: dockerNetwork.name, disabled: this.isDetails }, Validators.required],
        'driver':  [{ value: driver, disabled: this.isDetails }],
        'ipamDriver':  [{ value: ipamDriver, disabled: this.isDetails }],
        'subnet1':  [{ value: '', disabled: this.isDetails }],
        'ipRange1':  [{ value: '', disabled: this.isDetails }],
        'gateway1':  [{ value: '', disabled: this.isDetails }],
        'deviceName1':  [{ value: '', disabled: this.isDetails }],
        'ipAddress1':  [{ value: '', disabled: this.isDetails }],
        'ipamOption1':  [{ value: '', disabled: this.isDetails }],
        'ipamOptionValue1':  [{ value: '', disabled: this.isDetails }],
        'label1':  [{ value: '', disabled: this.isDetails }],
        'labelValue1':  [{ value: '', disabled: this.isDetails }],
        'option1':  [{ value: '', disabled: this.isDetails }],
        'optionValue1':  [{ value: '', disabled: this.isDetails }],
        'ipv6':  [{ value: dockerNetwork.ipv6, disabled: this.isDetails }],
        'internal':  [{ value: dockerNetwork.internal, disabled: this.isDetails }],
        'attachable':  [{ value: dockerNetwork.attachable, disabled: this.isDetails }]
      });
    this.name = this.networkForm.controls['name'];
    this.driver = this.networkForm.controls['driver'];
    this.ipamDriver = this.networkForm.controls['ipamDriver'];
    this.subnet1 = this.networkForm.controls['subnet1'];
    this.ipRange1 = this.networkForm.controls['ipRange1'];
    this.gateway1 = this.networkForm.controls['gateway1'];
    this.deviceName1 = this.networkForm.controls['deviceName1'];
    this.ipAddress1 = this.networkForm.controls['ipAddress1'];
    this.ipamOption1 = this.networkForm.controls['ipamOption1'];
    this.ipamOptionValue1 = this.networkForm.controls['ipamOptionValue1'];
    this.label1 = this.networkForm.controls['label1'];
    this.labelValue1 = this.networkForm.controls['labelValue1'];
    this.option1 = this.networkForm.controls['option1'];
    this.optionValue1 = this.networkForm.controls['optionValue1'];
    this.ipv6 = this.networkForm.controls['ipv6'];
    this.internal = this.networkForm.controls['internal'];
    this.attachable = this.networkForm.controls['attachable'];
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

  openDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: 'Remove Network'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.networksService.removeNetwork(this.name.value)
          .subscribe(() => {
            this.router.navigate(['/networks']);
        });
      }
    });
  }


}
