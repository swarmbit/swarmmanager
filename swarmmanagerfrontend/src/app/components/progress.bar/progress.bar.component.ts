import { Component, Input } from '@angular/core';
import { ProgressBarService } from '../../services/progress.bar/progress.bar.service';

@Component({
  selector: 'app-progress-bar',
  styleUrls: ['progress.bar.component.css'],
  templateUrl: 'progress.bar.component.html'
})
export class ProgressBarComponent {
  @Input('color') color = 'primary';

  constructor(public progressBarService: ProgressBarService) {
  }
}
