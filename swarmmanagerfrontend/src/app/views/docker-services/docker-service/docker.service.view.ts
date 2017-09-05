
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { DockerServicesService } from '../../../services/docker-services/docker.services.service';
import { DockerService } from '../../../services/docker-services/model/docker.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderService } from '../../../services/header/header.service';
import { BaseView } from '../../base.view';
import { CleanServiceImagePipe } from '../pipes/clean.service.image.pipe';
import { MdSnackBar } from '@angular/material';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-services-details',
  styleUrls: ['docker.service.view.css'],
  templateUrl: 'docker.service.view.html'
})
export class DockerServiceView extends BaseView implements OnInit, OnDestroy {

  service: DockerService;
  isCreateService: boolean;
  isEditable: boolean;
  id: string;
  errorMessage: string;
  title: string;
  cleanServiceImagePipe: CleanServiceImagePipe;
  private sub: any;

  selectedMode: string;
  image: string;

  constructor(private dockerServicesService: DockerServicesService,
              private router: Router,
              private route: ActivatedRoute,
              public snackBar: MdSnackBar,
              public dialog: MdDialog,
              headerService: HeaderService) {
    super(headerService);
    this.cleanServiceImagePipe = new CleanServiceImagePipe();
    this.service = new DockerService();
    this.isCreateService = this.router.isActive('/service', true);
    this.title = '';
    if (this.isCreateService) {
      this.title = 'Create Service';
      this.isEditable = true;
    } else {
      this.title = 'Service';
    }
    this.setViewName(this.title);
    this.enableBackArrow('/services');
  }

  openDialog(id: string, name: string): void {
    this.dialog.open(DockerServiceRemoveDialog, {
      width: '15rem',
      data: { id: id, name: name }
    });
  }


  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.getService(this.id);
      }
    });
  }

  setEditable() {
    this.isEditable = true;
  }

  saveChanges() {
    this.isEditable = false;
    this.service.image = this.image;
    this.snackBar.open('Service ' + this.service.name + ' was successfully saved!', 'Service saved', {
      duration: 2000,
    });
    this.dockerServicesService.updateService(this.id, this.service).subscribe();
  }

  submitService() {
    if (this.selectedMode === 'global') {
      this.service.global = true;
    }
    this.service.image = this.image;
    this.dockerServicesService.createService(this.service).subscribe();
    this.snackBar.open('Service ' + this.service.name + ' was successfully created!', 'Service created', {
      duration: 2000,
    });
    this.router.navigate(['/services']);
  }

  getService(id: string) {
    this.dockerServicesService.getService(id)
      .subscribe(
        service => {
          this.service = service;
          this.image = this.cleanServiceImagePipe.transform(this.service.image);
          this.setViewName(this.title + ' ' + this.service.name);
          if (this.service.global) {
            this.selectedMode = 'global';
          } else {
            this.selectedMode = 'replicated';
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  removeService(): void {
    this.openDialog(this.id, this.service.name);
  }

  cancelEdit(): void {
    this.isEditable = false;
  }

  ngOnDestroy(): void {
    this.disableBackArrow();
    this.sub.unsubscribe();
  }

}

@Component({
  selector: 'app-docker-service-remove-dialog',
  templateUrl: 'docker.service.remove.dialog.html',
})
export class DockerServiceRemoveDialog {

  constructor(
    public dialogRef: MdDialogRef<DockerServiceRemoveDialog>,
    @Inject(MD_DIALOG_DATA) public data: any,
    private dockerServicesService: DockerServicesService,
    private router: Router,
    public snackBar: MdSnackBar) {
  }

  onYesClick(id: string, name: string): void {
    this.dockerServicesService.deleteService(id).subscribe();
    this.router.navigate(['/services']);
    this.snackBar.open('Service ' + name + ' was successfully removed!', 'Service Removed', {
      duration: 2000,
    });
    this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
