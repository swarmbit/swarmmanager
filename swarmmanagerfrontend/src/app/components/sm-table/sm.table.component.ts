import { Component, Input, OnInit } from '@angular/core';
import { SmTableContent } from './sm.table.content';

@Component({
  selector: 'sm-table',
  templateUrl: 'sm.table.component.html'
})
export class SmTableComponent implements OnInit {

  @Input() smTableContent: SmTableContent;

  columns: string[];
  properties: string[];

  ngOnInit(): void {
    this.columns = [];
    this.properties = [];
    this.smTableContent.columns.forEach((value: string, key: string) => {
      this.columns.push(key);
      this.properties.push(value);
    });
  }

}
