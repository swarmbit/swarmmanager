import { BehaviorSubject } from 'rxjs/BehaviorSubject';

export class SmDatabase {

  /** Stream that emits whenever the data has been modified. */
  private dataChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);

  get stream(): BehaviorSubject<any[]> {
    return this.dataChange;
  }

  get data(): any[] {
    return this.dataChange.value;
  }

  addData(data: any[]) {
    for (let i = 0; i < data.length; i++) {
      this.addEntry(data[i]);
    }
  }

  addEntry(value: any): void {
    const copiedData = this.data.slice();
    copiedData.push(value);
    this.dataChange.next(copiedData);
  }

  replaceData(data: any[]): void {
    while(this.data.length > 0) {
      this.data.pop();
    }
    this.addData(data)
  }
}
