import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { NodesView } from '../../views/nodes/nodes.view';
import { NetworksView } from '../../views/networks/networks.view';
import { ManageNetworkView } from '../../views/networks/manage/manage.network.view';
import { DockerNetworkResolver } from '../../resolvers/docker/networks/docker.network.resolver';
import { ServicesView } from '../../views/services/services.view';
import { ManageServicesView } from '../../views/services/manage/manage.services.view';
import { DockerServiceResolver } from '../../resolvers/docker/services/docker.service.resolver';
import { LogsView } from '../../views/services/logs/logs.view';
import { DockerServiceLogsResolver } from '../../resolvers/docker/services/docker.service.logs.resolver';
import { ProfileView } from '../../views/profile/profile.view';
import { UserManagementView } from '../../views/user.management/user.management.view';
import { ShellComponent } from '../../components/shell/shell.component';
import { AuthGuard } from '../../guards/auth.guard';
import { LoginView } from '../../views/login/login.view';
import { VisitorGuard } from '../../guards/visitor.guard';
import { AdminGuard } from '../../guards/admin.guard';
import { LoginGuard } from '../../guards/login.guard';
import { CreateUserView } from '../../views/create.user/create.user.view';
import { NotFoundView } from '../../views/not.found/not.found.view';
import { ForgotView } from '../../views/forgot/forgot.view';
import { ConfigsSecretsView } from '../../views/configs.secrets/configs.secrets.view';
import { ManageConfigSecretView } from '../../views/configs.secrets/manage/manage.config.secret.view';
import { DockerConfigsResolver } from '../../resolvers/docker/configs/docker.configs.resolver';
import { DockerSecretsResolver } from '../../resolvers/docker/secrets/docker.secrets.resolver';

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginView,
    canActivate: [LoginGuard]
  },
  {
    path: 'createUser',
    component: CreateUserView,
    canActivate: [LoginGuard]
  },
  {
    path: 'forgot',
    component: ForgotView,
    canActivate: [LoginGuard]
  },
  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        redirectTo: 'services',
        pathMatch: 'full'
      },
      {
        path: 'services',
        component: ServicesView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Services',
        }
      },
      {
        path: 'services/create',
        component: ManageServicesView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Create Service',
          action: 'create'
        }
      },
      {
        path: 'configs',
        component: ConfigsSecretsView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Configs',
          type: 'config'
        }
      },
      {
        path: 'configs/create',
        component: ManageConfigSecretView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Create Config',
          type: 'config'
        }
      },
      {
        path: 'configs/:name',
        component: ManageConfigSecretView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          action: 'manage',
          type: 'config'
        },
        resolve: {
          object: DockerConfigsResolver
        }
      },
      {
        path: 'secrets',
        component: ConfigsSecretsView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Secrets',
          type: 'secret'
        }
      },
      {
        path: 'secrets/create',
        component: ManageConfigSecretView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Create Secret',
          type: 'secret'
        }
      },
      {
        path: 'secrets/:name',
        component: ManageConfigSecretView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          action: 'manage',
          type: 'secret'
        },
        resolve: {
          object: DockerSecretsResolver
        }
      },
      {
        path: 'services/:name',
        component: ManageServicesView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          action: 'manage'
        },
        resolve: {
          dockerService: DockerServiceResolver
        }
      },
      {
        path: 'services/:name/logs',
        component: LogsView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Service Logs',
        },
        resolve: {
          dockerServiceLogs: DockerServiceLogsResolver
        }
      },
      {
        path: 'networks',
        component: NetworksView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Networks',
        }
      },
      {
        path: 'networks/create',
        component: ManageNetworkView,
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Create Network',
          action: 'create'
        }
      },
      {
        path: 'networks/:name',
        component: ManageNetworkView,
        canActivate: [AuthGuard, VisitorGuard],
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
        canActivate: [AuthGuard, VisitorGuard],
        data: {
          title: 'Nodes',
        }
      },
      {
        path: 'user/profile',
        component: ProfileView,
        canActivate: [AuthGuard],
        data: {
          title: 'Profile',
        }
      },
      {
        path: 'users',
        component: UserManagementView,
        canActivate: [AuthGuard, AdminGuard],
        data: {
          title: 'User Management',
        }
      } ]
  },
  {
    path: '**',
    component: NotFoundView
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
