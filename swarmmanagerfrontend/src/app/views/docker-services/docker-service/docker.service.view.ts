
import { Component, OnDestroy, OnInit } from '@angular/core';
import { DockerServicesService } from '../../../services/docker-services/docker.services.service';
import { DockerService } from '../../../services/docker-services/model/docker.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderService } from '../../../services/header/header.service';
import { BaseView } from '../../base.view';
import { CleanServiceImagePipe } from '../pipes/clean.service.image.pipe';

@Component({
  selector: 'app-services-details',
  styleUrls: ['docker.service.view.css'],
  templateUrl: 'docker.service.view.html'
})
export class DockerServiceView extends BaseView implements OnInit, OnDestroy {

  service: DockerService;
  isCreateService: boolean;
  isEditable: boolean;
  id: number;
  errorMessage: string;
  title: string;
  cleanServiceImagePipe: CleanServiceImagePipe;
  private sub: any;

  selectedMode: string;
  image: string;

  constructor(private dockerServicesService: DockerServicesService,
              private router: Router,
              private route: ActivatedRoute,
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
    this.dockerServicesService.updateService(this.id, this.service).subscribe();
  }

  submitService() {
    if (this.selectedMode === 'global') {
      this.service.global = true;
    }
    this.service.image = this.image;
    this.dockerServicesService.createService(this.service).subscribe();
    this.router.navigate(['/services']);
  }

  getService(id: number) {
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

  deleteService(): void {
    this.dockerServicesService.deleteService(this.id).subscribe();
    this.router.navigate(['/services']);
  }

  ngOnDestroy(): void {
    this.disableBackArrow();
    this.sub.unsubscribe();
  }

}
