export class DockerSwarm {
  private static VERSION_1_25 = 1.25;
  private static VERSION_1_26 = 1.26;
  private static VERSION_1_27 = 1.27;
  private static VERSION_1_28 = 1.28;
  private static VERSION_1_29 = 1.29;
  private static VERSION_1_30 = 1.30;

  name: string;
  id: string;
  apiVersion: string;

  greaterThenVersion25(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_25);
  }

  equalsOrGreaterThenVersion26(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_26);
  }

  equalsOrGreaterThenVersion27(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_27);
  }

  equalsOrGreaterThenVersion28(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_28);
  }

  equalsOrGreaterThenVersion29(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_29);
  }

  equalsOrGreaterThenVersion30(): boolean {
    return this.compareVersions(DockerSwarm.VERSION_1_30);
  }

  private compareVersions(versionToCompare: number): boolean {
      if (this.apiVersion) {
        const version: number = +this.apiVersion.substr(1);
        if (version >= versionToCompare) {
          return true;
        }
      }
      return false;
  }
}
