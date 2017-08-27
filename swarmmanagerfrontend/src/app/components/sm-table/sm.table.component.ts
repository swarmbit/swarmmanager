import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { DataSource } from '@angular/cdk';
import { SmTableContent } from './sm.table.content';
import { SmDatabase } from './sm.database';

@Component({
  selector: 'sm-table',
  templateUrl: 'sm.table.component.html'
})
export class SmTableComponent implements OnInit {

  @Input() smTableContent: SmTableContent;

  columns: string[];
  properties: string[];
  dataSource: SmDataSource | null;

  ngOnInit(): void {
    this.columns = [];
    this.properties = [];
    this.smTableContent.columns.forEach((value: string, key: string) => {
      this.columns.push(key);
      this.properties.push(value);
    });
    this.dataSource = new SmDataSource(this.smTableContent.database);
  }

}


/**
 * Data source to provide what data should be rendered in the table. Note that the data source
 * can retrieve its data in any way. In this case, the data source is provided a reference
 * to a common data base, ExampleDatabase. It is not the data source's responsibility to manage
 * the underlying data. Instead, it only needs to take the data and send the table exactly what
 * should be rendered.
 */
export class SmDataSource extends DataSource<any> {
  constructor(private _smDatabase: SmDatabase) {
    super();
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<any[]> {
    return this._smDatabase.stream;
  }

  disconnect() {}
}
