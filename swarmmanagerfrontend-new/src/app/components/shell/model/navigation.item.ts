export class NavigationItem {
  public name: string;
  public link: string;
  public icon: string;

  constructor(name: string, link: string, icon: string) {
    this.name = name;
    this.link = link;
    this.icon = icon;
  }
}
