export class DockerNodeSummary {
  id: string;
  hostname: string;
  status: string;
  availability: string;
  managerReachability: string;
  numberOfRunningTasks: string;
  leader: boolean;
  manager: boolean;
}
