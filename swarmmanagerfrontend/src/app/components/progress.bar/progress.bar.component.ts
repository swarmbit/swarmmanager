import { Component } from '@angular/core';
import { ProgressBarService } from '../../services/progress.bar/progress.bar.service';

@Component({
  selector: 'app-progress-bar',
  styleUrls: ['progress.bar.component.css'],
  templateUrl: 'progress.bar.component.html'
})
export class ProgressBarComponent {
  constructor(public progressBarService: ProgressBarService) {
  }
}
