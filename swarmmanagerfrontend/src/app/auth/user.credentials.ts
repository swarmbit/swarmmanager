export class UserCredentials {

  private username: string;

  private password: string;


  getUsername(): string {
    return this.username;
  }

  setUsername(username: string) {
    this.username = username;
  }

  getPassword(): string {
    return this.password;
  }

  setPassword(password: string) {
    this.password = btoa(password);
  }

}
