export class User {
  username = '';
  displayName = '';
  roles: string[] = [];

  isAdmin(): boolean {
    return this.roles.indexOf('ADMIN') > -1;
  }

  isUser(): boolean {
    return this.roles.indexOf('USER') > -1 && !this.isAdmin();
  }

  isVisitor(): boolean {
    return this.roles.indexOf('VISITOR') > -1 && !this.isUser();
  }
}
