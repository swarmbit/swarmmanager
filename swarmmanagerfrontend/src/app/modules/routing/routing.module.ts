import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { NodesView } from '../../views/nodes/nodes.view';
import { NetworksView } from '../../views/networks/networks.view';
import { ManageNetworkView } from '../../views/networks/manage/manage.network.view';
import { DockerNetworkResolver } from '../../resolvers/docker/networks/docker.network.resolver';
import { ServicesView } from '../../views/services/services.view';
import { ManageServicesView } from '../../views/services/manage/manage.services.view';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/services',
    pathMatch: 'full'
  },
  {
    path: 'services',
    component: ServicesView,
    data: {
      title: 'Services',
    }
  },
  {
    path: 'services/create',
    component: ManageServicesView,
    data: {
      title: 'Create Service',
      action: 'create'
    }
  },
  {
    path: 'services/:name',
    component: ManageServicesView,
    data: {
      action: 'manage'
    },
    resolve: {
      dockerNetwork: DockerNetworkResolver
    }
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
