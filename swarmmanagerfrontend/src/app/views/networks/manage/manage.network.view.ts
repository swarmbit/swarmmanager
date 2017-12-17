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

@Component({
  selector: 'app-manage-network',
  styleUrls: ['manage.network.view.scss'],
  templateUrl: 'manage.network.view.html'
})
export class ManageNetworkView extends BaseView {

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
      this.subscriptions.push(this.networksService.createNetwork(this.getNewDockerNetwork(this.networkForm.value)).subscribe(
        () => {
          this.router.navigate(['/networks']);
        }
      ));
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
        'labels': new FormArray([
          new FormGroup({
            'name': new FormControl({value: null, disabled: this.isDetails}),
            'value': new FormControl({value: null, disabled: this.isDetails}),
          })
        ])
      });
  }

  addLabel(): void {
    const control = new FormGroup({
      'name': new FormControl({value: null, disabled: this.isDetails}),
      'value': new FormControl({value: null, disabled: this.isDetails}),
    });
    (<FormArray>this.networkForm.get('labels')).push(control);
  }

  removeLabel(index: number): void {
    (<FormArray>this.networkForm.get('labels')).removeAt(index);
  }

  getNewDockerNetwork(values): DockerNetwork {
    const dockerNetwork = new DockerNetwork();
    dockerNetwork.name = values['name'];
    dockerNetwork.driver = values['drive'];
    dockerNetwork.ipamDriver = values['ipamDriver'];
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
