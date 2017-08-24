
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ServicesService } from '../../../services/docker-services/docker.services.service';
import { Service } from '../../../services/docker-services/model/service';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderService } from '../../../services/header/header.service';
import { BaseView } from '../../base.view';

@Component({
  selector: 'app-services-details',
  styleUrls: ['docker.services.details.view.css'],
  templateUrl: 'docker.services.details.view.html'
})
export class DockerServicesDetailsView extends BaseView implements OnInit, OnDestroy {

  service: Service;
  isCreateService: boolean;
  isEditable: boolean;
  id: number;
  errorMessage: string;

  private sub: any;

  constructor(private serviceService: ServicesService,
              private router: Router,
              private route: ActivatedRoute,
              headerService: HeaderService) {
    super(headerService);
    this.serviceService = serviceService;
    this.service = new Service();
    this.isCreateService = this.router.isActive('/services/create', false);
    let title = '';
    if (this.isCreateService) {
      title = 'Create Service';
      this.isEditable = true;
    } else {
      title = 'Service Details';
    }
    this.setViewName(title);
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
  }

  submitService() {
    this.serviceService.createService(this.service).subscribe();
    this.router.navigate(['/services']);
  }

  getService(id: number) {
    this.serviceService.getService(id)
      .subscribe(
        service => {
          this.service = service;
        },
        error =>  this.errorMessage = <any>error);
  }

  ngOnDestroy(): void {
    this.disableBackArrow();
    this.sub.unsubscribe();
  }

}
