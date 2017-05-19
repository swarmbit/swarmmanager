import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'cleanServiceImagePipe'})
export class CleanServiceImagePipe implements PipeTransform {
  transform(value: string): string {
    let newValue = value;
    console.log(value);
    if (value.indexOf("@") > 0) {
      newValue = value.substring(0, value.indexOf("@"));
    }
    return newValue;
  }
}
