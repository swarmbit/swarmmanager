
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RoutingService } from '../../routing/routing.service';
import { ServicesService } from '../services-service/services.service';
import { BackArrow } from '../../routing/back.arrow';
import { Service } from '../model/service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-services-details',
  styleUrls: ['services.details.component.css'],
  templateUrl: 'services.details.component.html'
})
export class ServicesDetailsComponent implements OnInit, OnDestroy {

  service: Service;
  title: string;
  isCreateService: boolean;
  isEditable: boolean;
  id: number;
  errorMessage: string;

  private sub: any;


  constructor(private serviceService: ServicesService,
              private routingService: RoutingService,
              private router: Router,
              private route: ActivatedRoute) {
    this.routingService = routingService;
    this.serviceService = serviceService;
    this.service = new Service();
    this.isCreateService = this.router.isActive('/services/create', false);
    if (this.isCreateService) {
      this.title = 'Create Service';
      this.isEditable = true;
    } else {
      this.title = 'Service Details';
    }
  }

  ngOnInit(): void {
    this.routingService.setHeaderName(this.title);
    this.routingService.setBackArrow(new BackArrow(true, '/services'));
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
    this.routingService.setBackArrow(new BackArrow(false, ''));
    this.sub.unsubscribe();
  }

}
