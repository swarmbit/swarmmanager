import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { NodesView } from '../../views/nodes/nodes.view';
import { NetworksView } from '../../views/networks/networks.view';
import { ManageNetworkView } from '../../views/networks/manage/manage.network.view';
import { DockerNetworkResolver } from '../../resolvers/docker/networks/docker.network.resolver';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/networks',
    pathMatch: 'full'
  },
  {
    path: 'networks',
    component: NetworksView,
    data: {
      title: 'Networks',
    }
  },
  {
    path: 'networks/create',
    component: ManageNetworkView,
    data: {
      title: 'Create Network',
      action: 'create'
    }
  },
  {
    path: 'networks/:name',
    component: ManageNetworkView,
    data: {
      action: 'manage'
    },
    resolve: {
      dockerNetwork: DockerNetworkResolver
    }
  },
  {
    path: 'nodes',
    component: NodesView,
    data: {
      title: 'Nodes',
    }
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes),
  ],
  exports: [
    RouterModule
  ]
})
export class RoutingModule {
}
