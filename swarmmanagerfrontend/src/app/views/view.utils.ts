export class ViewUtils {

  public static sortByName = (value1, value2) => {
    if (value1.name < value2.name ) {
      return -1;
    }
    if (value1.name > value2.name ) {
      return 1;
    }
    return 0;
  }

  public static getFilteredArray(filter: string, array: any[], objectProperty): any[] {
    const arrayTmp = [];
    for (const object of array) {
      if (object[objectProperty].indexOf(filter) > -1) {
        arrayTmp.push(object);
      }
    }
    return arrayTmp;
  }

}
