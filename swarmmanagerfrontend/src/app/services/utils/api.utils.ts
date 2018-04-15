export class ApiUtils {

  public static getApiError(error: any): string {
    if (error.status === 400 && error.error) {
      const errorObj = error.error;
      let errorsArr = null;
      if ('string' === typeof errorObj) {
        errorsArr = JSON.parse(errorObj).errors;
      } else {
        errorsArr = errorObj.errors;
      }
      if (errorsArr && errorsArr.length == 1) {
        return errorsArr[0].code;
      }
    }
    return null;
  }

}
