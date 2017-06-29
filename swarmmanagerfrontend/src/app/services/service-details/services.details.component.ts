
import { Component, OnDestroy, OnInit } from '@angular/core';
import { HeaderService } from '../../shell/header/header-service/header.service';
import { ServicesService } from '../services-service/services.service';
import { BackArrow } from '../../shell/header/header-service/back.arrow';
import { Service } from '../model/service';
import {ActivatedRoute, Router} from '@angular/router';
import {HeaderInfo} from '../../shell/header/header-service/header.info';

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
  headerInfo: HeaderInfo;

  private sub: any;


  constructor(private serviceService: ServicesService,
              private headerService: HeaderService,
              private router: Router,
              private route: ActivatedRoute) {
    this.serviceService = serviceService;
    this.service = new Service();
    this.isCreateService = this.router.isActive('/services/create', false);
    if (this.isCreateService) {
      this.title = 'Create Service';
      this.isEditable = true;
    } else {
      this.title = 'Service Details';
    }
    this.headerService = headerService;
    this.headerInfo = new HeaderInfo();
    this.headerInfo.currentViewName = this.title;
    this.headerInfo.backArrow = new BackArrow(true, '/services');
    this.headerService.setHeaderInfo(this.headerInfo);
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
    this.headerInfo.backArrow = new BackArrow(false, '');
    this.headerService.setHeaderInfo(this.headerInfo);
    this.sub.unsubscribe();
  }

}
