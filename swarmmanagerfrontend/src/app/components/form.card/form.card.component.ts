import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-card-component',
  templateUrl: 'form.card.component.html'
})
export class FormCardComponent {
  @Input()
  public title: string;

  @Input()
  public expanded: boolean;

}
