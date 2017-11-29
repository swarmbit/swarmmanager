import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { NodesView } from '../../views/nodes/nodes.view';
import { NetworksView } from '../../views/networks/networks.view';
import { CreateNetworkView } from '../../views/networks/create/create.network.view';

const appRoutes: Routes = [
  { path: '', redirectTo: '/networks', pathMatch: 'full'},
  { path: 'networks', component: NetworksView },
  { path: 'networks/create', component: CreateNetworkView },
  { path: 'nodes', component: NodesView }
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
