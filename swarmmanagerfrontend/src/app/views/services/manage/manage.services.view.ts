import { Component } from '@angular/core';
import { HeaderService } from '../../../services/header/header.service';
import { DockerSwarmService } from '../../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../../services/user/user.service';
import { BaseView } from '../../base.view';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../../../components/confirmation.dialog/confirmation.dialog.component';
import { DockerServicesService } from '../../../services/docker/services/docker.services.service';
import { DockerService } from '../../../services/docker/services/docker.service';
import { CleanServiceImagePipe } from '../../../pipes/clean.service.image.pipe';

@Component({
  selector: 'app-manage-service',
  styleUrls: ['manage.services.view.scss'],
  templateUrl: 'manage.services.view.html'
})
export class ManageServicesView extends BaseView {

  formErrorMessage = 'Please check invalid fields!';
  formInvalid: boolean;
  isDetails: boolean;
  serviceForm: FormGroup;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private dockerServicesService: DockerServicesService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog
  ) {
    super(headerService, route, swarmService, userService);
    super.enableBackArrow('/services');
    this.isDetails = route.snapshot.data[ 'action' ] === 'manage';
    if (this.isDetails) {
      this.subscriptions.push(this.route.data
        .subscribe(
          (data: Data) => {
            const dockerService = data['dockerService'];
            super.setViewName('Service ' + dockerService.name);
            this.createForm(dockerService);
          }
      ));
    } else {
      this.createForm(new DockerService());
    }
  }

  createService(): void {
    if (this.serviceForm.valid) {
      this.formInvalid = false;
      this.subscriptions.push(this.dockerServicesService.createService(this.getNewDockerService(this.serviceForm.value)).subscribe(
        () => {
          this.router.navigate(['/services']);
        }
      ));
    } else {
      this.formInvalid = true;
    }
  }

  createForm(dockerService: DockerService): void {
    const imagePipe = new CleanServiceImagePipe();
    this.serviceForm = new FormGroup({
      'name': new FormControl({ value: dockerService.name, disabled: this.isDetails }),
      'image': new FormControl({ value: imagePipe.transform(dockerService.image), disabled: this.isDetails })
    });
  }

  getNewDockerService(values): DockerService {
    const dockerService = new DockerService();
    dockerService.name = values['name'];
    dockerService.image = values['image'];
    return dockerService;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: 'Remove Service'
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

}
