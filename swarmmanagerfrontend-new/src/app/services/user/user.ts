export class User {
  username = '';
  displayName = '';
  roles: string[] = [];
  role: string;

  isAdmin(): boolean {
    return this.roles.indexOf('ADMIN') > -1;
  }

  isUser(): boolean {
    return this.roles.indexOf('USER') > -1;
  }

  isVisitor(): boolean {
    return this.roles.indexOf('VISITOR') > -1;
  }

  isNone(): boolean {
    return this.roles.indexOf('NONE') > -1;
  }
}
