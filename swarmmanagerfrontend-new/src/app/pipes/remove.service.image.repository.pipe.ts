import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'removeServiceImageRepositoryPipe'})
export class RemoveServiceImageRepositoryPipe implements PipeTransform {
  transform(value: string): string {
    let newValue = value;
    if (value && value.indexOf('@') > 0) {
      newValue = value.substring(0, value.indexOf('@'));
    }
    if (newValue && newValue.indexOf('/') > 0) {
      newValue = newValue.substring(newValue.indexOf('/') + 1, newValue.length);
    }
    return newValue;
  }
}
