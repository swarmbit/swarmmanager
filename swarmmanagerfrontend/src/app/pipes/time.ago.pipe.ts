import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'timeAgo'})
export class TimeAgoPipe implements PipeTransform {
  transform(value: any): string {
    const currentTime = new Date();
    const duration = currentTime.getTime() - value;
    const seconds = Math.floor(((duration / 1000) % 60));
    const minutes = Math.floor((duration / (1000 * 60)) % 60);
    const hours = Math.floor(((duration / (1000 * 60 * 60)) % 60));
    const days = Math.floor(((duration / (1000 * 60 * 60 * 24)) % 24));
    let result = '';
    if (days > 1) {
      result = days + ' days, ';
    } else if (days === 1) {
      result = days + ' day, ';
    }

    if (hours > 1) {
      result += hours + ' hours, ';
    } else if (hours === 1) {
      result += hours + ' hour, ';
    }

    if (minutes > 1) {
      result += minutes + ' minutes and ';
    } else if (minutes === 1) {
      result += minutes + ' minute and ';
    }

    if (seconds === 0 || seconds > 1) {
      result += seconds + ' seconds ago';
    } else if (seconds === 1) {
      result += seconds + ' second ago';
    }
    return result;
  }
}
