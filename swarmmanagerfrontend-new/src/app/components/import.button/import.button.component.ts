import { Component, EventEmitter, Input, Output, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-import-button',
  styleUrls: ['import.button.component.css'],
  templateUrl: 'import.button.component.html'
})
export class ImportButtonComponent {

  @Input('text') text = 'Import';

  @Output()
  valueSelected: EventEmitter<string>;

  @ViewChild('importInput')
  importInput: ElementRef;

  public constructor() {
    this.valueSelected = new EventEmitter();
  }

  loadFile(event: any): void {
    const files = event.target.files;
    if (files && files.length > 0) {
      const file = files[ 0 ];
      const fr = new FileReader();
      fr.onload = (e: any) => {
        this.valueSelected.emit(e.target.result);
      };
      fr.readAsText(file);
    }
  }

  import(): void {
    this.importInput.nativeElement.click();
  }

}
