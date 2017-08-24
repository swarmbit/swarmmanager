export class UserCredentials {

  private username: string;

  private password: string;


  getUsername(): string {
    return this.username;
  }

  setUsername(username: string) {
    this.username = username;
  }

  setPassword(password: string) {
    this.password = btoa(password);
  }

}
