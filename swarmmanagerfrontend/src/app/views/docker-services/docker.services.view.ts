
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ServicesService } from '../../services/docker-services/docker.services.service';
import { ServiceSummary } from '../../services/docker-services/model/service.summary';
import { BehaviorSubject, Observable, Subscription } from 'rxjs/Rx';
import { DataSource } from '@angular/cdk';
import { HeaderInfo } from '../../services/header/header.info';
import { HeaderService } from '../../services/header/header.service';

@Component({
  selector: 'app-services',
  styleUrls: ['docker.services.view.scss'],
  templateUrl: 'docker.services.view.html'
})
export class DockerServicesView implements OnInit, OnDestroy {

  headerService: HeaderService;
  serviceService: ServicesService;
  errorMessage: string;
  servicesSummary: ServiceSummary[];
  subscription: Subscription;
  filter: string;
  displayedColumns = ['userId', 'userName', 'progress', 'color'];
  exampleDatabase = new ExampleDatabase();
  dataSource: ExampleDataSource | null;

  constructor(serviceService: ServicesService, headerService: HeaderService) {
    this.headerService = headerService;
    const headerInfo = new HeaderInfo();
    headerInfo.currentViewName = 'Services';
    this.headerService.setHeaderInfo(headerInfo);
    this.serviceService = serviceService;
    this.servicesSummary = [];
    this.filter = '';
  }

  ngOnInit(): void {
    this.dataSource = new ExampleDataSource(this.exampleDatabase);
    this.executeServiceLs();
    this.subscription = Observable
      .interval(5000)
      .subscribe(() => this.executeServiceLs());
  }

  executeServiceLs() {
    this.serviceService.executeServiceLs()
      .subscribe(
        servicesSummary => {
          this.servicesSummary = servicesSummary;
        },
        error =>  this.errorMessage = <any>error);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  clearFilter() {
    this.filter = '';
  }

  servicesSummaryFiltered(): ServiceSummary[] {
    const servicesSummaryFiltered = [];
    for (const serviceSummary of this.servicesSummary) {
      if (serviceSummary.name.indexOf(this.filter.toLowerCase()) > -1 ||
            serviceSummary.id.indexOf(this.filter.toLowerCase()) > -1) {
        servicesSummaryFiltered.push(serviceSummary);
      }
    }
    return servicesSummaryFiltered;
  }

}

/** Constants used to fill up our data base. */
const COLORS = ['maroon', 'red', 'orange', 'yellow', 'olive', 'green', 'purple',
  'fuchsia', 'lime', 'teal', 'aqua', 'blue', 'navy', 'black', 'gray'];
const NAMES = ['Maia', 'Asher', 'Olivia', 'Atticus', 'Amelia', 'Jack',
  'Charlotte', 'Theodore', 'Isla', 'Oliver', 'Isabella', 'Jasper',
  'Cora', 'Levi', 'Violet', 'Arthur', 'Mia', 'Thomas', 'Elizabeth'];

export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}

/** An example database that the data source uses to retrieve data for the table. */
export class ExampleDatabase {
  /** Stream that emits whenever the data has been modified. */
  dataChange: BehaviorSubject<UserData[]> = new BehaviorSubject<UserData[]>([]);
  get data(): UserData[] { return this.dataChange.value; }

  constructor() {
    // Fill up the database with 100 users.
    for (let i = 0; i < 100; i++) { this.addUser(); }
  }

  /** Adds a new user to the database. */
  addUser() {
    const copiedData = this.data.slice();
    copiedData.push(this.createNewUser());
    this.dataChange.next(copiedData);
  }

  /** Builds and returns a new User. */
  private createNewUser() {
    const name =
      NAMES[Math.round(Math.random() * (NAMES.length - 1))] + ' ' +
      NAMES[Math.round(Math.random() * (NAMES.length - 1))].charAt(0) + '.';

    return {
      id: (this.data.length + 1).toString(),
      name: name,
      progress: Math.round(Math.random() * 100).toString(),
      color: COLORS[Math.round(Math.random() * (COLORS.length - 1))]
    };
  }
}

/**
 * Data source to provide what data should be rendered in the table. Note that the data source
 * can retrieve its data in any way. In this case, the data source is provided a reference
 * to a common data base, ExampleDatabase. It is not the data source's responsibility to manage
 * the underlying data. Instead, it only needs to take the data and send the table exactly what
 * should be rendered.
 */
export class ExampleDataSource extends DataSource<any> {
  constructor(private _exampleDatabase: ExampleDatabase) {
    super();
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<UserData[]> {
    return this._exampleDatabase.dataChange;
  }

  disconnect() {}
}
