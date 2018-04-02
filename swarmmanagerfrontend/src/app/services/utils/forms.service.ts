import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';

@Injectable()
export class FormsService {

  public timeUnits = {
    'ns': {
      order: 0,
      value: 1000,
      next: 'us'
    },
    'us': {
      order: 1,
      value: 1000,
      previous: 'ns',
      next: 'ms'
    },
    'ms': {
      order: 2,
      value: 1000,
      previous: 'us',
      next: 's'
    },
    's': {
      order: 3,
      value: 1000,
      previous: 'ms',
      next: 'm'
    },
    'm': {
      order: 4,
      value: 60,
      previous: 's',
      next: 'h'
    },
    'h': {
      order: 5,
      value: 60,
      previous: 'm'
    }
  };

  public memoryUnits = {
    'Bytes': {
      order: 0,
    },
    'KB': {
      order: 1,
    },
    'MB': {
      order: 2,
    },
    'GB': {
      order: 3,
    }
  };

  parseOptionsToObjectField(object: any, values: any, field: string, first: string, second?: string): void {
    if (second) {
      for (const value of values[field]) {
        if (value[first]) {
          if (!object[field]) {
            object[field] = {};
          }
          object[field][value[first]] = value[second];
        }
      }
    } else {
      const result = [];
      for (const value of values[field]) {
        if (value[first] && (!(value[first] instanceof String) || value[first] != '')) {
          result.push(value[first]);
        }
      }
      object[field] = result;
    }
  }

  parseObjectFieldToOptions(formGroup: FormGroup, object: any, field: string, addExtra: boolean, disabled: boolean,
                          firstLabel?: string, secondLabel?: string): void {
    if (object && object[field]) {
      if (object[field] instanceof Array) {
        for (const value of object[field]) {
          this.addOption(formGroup, field, disabled, firstLabel, null, value);
        }
      } else {
        for (const prop of Object.keys(object[field])) {
          this.addOption(formGroup, field, disabled, firstLabel, secondLabel, prop, object[field][prop]);
        }
      }
    }
    if (addExtra) {
      this.addOption(formGroup, field, disabled, firstLabel, secondLabel);
    }
  }

  addOption(formGroup: FormGroup, type: string, disabled: boolean,
            firstLabel?: string, secondLabel?: string, firstValue?: any, secondValue?: any): void {

    const formGroupObj = {};

    if (firstLabel) {
      formGroupObj[firstLabel] = new FormControl({value: '', disabled: disabled});
      if (firstValue) {
        formGroupObj[firstLabel] = new FormControl({value: firstValue, disabled: disabled});
      }
    }

    if (secondLabel) {
      formGroupObj[secondLabel] = new FormControl({value: '', disabled: disabled});
      if (secondValue) {
        formGroupObj[secondLabel] = new FormControl({value: secondValue, disabled: disabled});
      }
    }

    (<FormArray>formGroup.get(type)).push(new FormGroup(formGroupObj));
  }
  removeFromArray(formGroup: FormGroup, type: string, index: number): void {
    (<FormArray>formGroup.get(type)).removeAt(index);
  }

  getValue(object?: any, field?: string, defaultValue?: string): string {
    let value = '';
    if (object && field) {
      if (defaultValue == undefined || defaultValue == null) {
        value = object[field] ? object[field]  : '';
      } else {
        value = object[field] ? object[field]  : defaultValue;
      }
    } else {
      if (defaultValue == undefined || defaultValue == null) {
        value = '';
      } else {
        value = defaultValue;
      }
    }
    return value;
  }

  calculateTimeValue(currentValue: number, currentUnit: string, newUnit: string): number {
    if (currentUnit === newUnit) {
      return currentValue;
    }

    let currentUnitValue = this.timeUnits[currentUnit];
    let previousOder = currentUnitValue.order;
    const order = this.timeUnits[newUnit].order;
    if (previousOder < order) {
      while (previousOder < order) {
        currentUnitValue = this.timeUnits[currentUnitValue.next];
        currentValue = currentValue / currentUnitValue.value;
        previousOder++;
      }
    } else if (previousOder > order) {
      while (previousOder > order) {
        currentValue = currentValue * currentUnitValue.value;
        currentUnitValue = this.timeUnits[currentUnitValue.previous];
        previousOder--;
      }
    }
    return currentValue;
  }

  calculateMemoryValue(currentValue: number, currentUnit: string, newUnit: string): number {
    if (currentUnit === newUnit) {
      return currentValue;
    }

    const currentUnitValue = this.memoryUnits[currentUnit];
    let previousOder = currentUnitValue.order;
    const order = this.memoryUnits[newUnit].order;
    if (previousOder < order) {
      while (previousOder < order) {
        currentValue = currentValue / 1024;
        previousOder++;
      }
    } else if (previousOder > order) {
      while (previousOder > order) {
        currentValue = currentValue * 1024;
        previousOder--;
      }
    }
    return currentValue;
  }
}

