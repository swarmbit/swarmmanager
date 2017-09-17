import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './modules/material/material.module';
import 'hammerjs';

import { AppComponent } from './app.component';
import { ShellComponent } from './shell/shell.component';
import { DockerServicesView } from './views/docker-services/docker.services.view';
import { RouterModule, Routes } from '@angular/router';
import { DashboardView } from './views/dashboard/dashboard.view';
import { SwarmView } from './views/swarm/swarm.view';
import { NodesView } from './views/nodes/nodes.view';
import { NetworksView } from './views/networks/networks.view';
import { SecretsView } from './views/secrets/secrets.view';
import { RegistriesComponent } from './views/registries/registries.view';
import { AuditView } from './views/audit/audit.view';
import { UserManagementView } from './views/user-management/user.management.view';
import { HeaderService } from './services/header/header.service';
import { DockerServicesService } from './services/docker-services/docker.services.service';
import { CleanServiceImagePipe } from './views/docker-services/pipes/clean.service.image.pipe';
import { DockerServiceRemoveDialog, DockerServiceView } from './views/docker-services/docker-service/edit/docker.service.view';
import { HeaderComponent } from './shell/header/header.component';
import { SidenavComponent } from './shell/navbar/navbar.component';
import { AuthService } from './services/auth/auth.service';
import { UserComponent } from './components/user/user.component';
import { LoginView } from './views/login/login.view';
import { SmTableComponent } from './components/sm-table/sm.table.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './services/auth/auth.interceptor';
import { DockerServiceLogsView } from './views/docker-services/docker-service/logs/docker.service.logs.view';

const appRoutes: Routes = [
  { path: '', redirectTo: '/services', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardView },
  { path: 'services', component: DockerServicesView },
  { path: 'service', component: DockerServiceView },
  { path: 'service/:id', component: DockerServiceView },
  { path: 'service/logs/:id', component: DockerServiceLogsView },
  { path: 'swarm', component: SwarmView },
  { path: 'nodes', component: NodesView },
  { path: 'networks', component: NetworksView },
  { path: 'secrets', component: SecretsView },
  { path: 'registries', component: RegistriesComponent },
  { path: 'audit', component: AuditView },
  { path: 'users', component: UserManagementView }
];
@NgModule({
  declarations: [
    AppComponent,
    ShellComponent,
    DockerServicesView,
    DockerServiceView,
    UserComponent,
    DashboardView,
    SwarmView,
    NodesView,
    NetworksView,
    SecretsView,
    RegistriesComponent,
    AuditView,
    UserManagementView,
    CleanServiceImagePipe,
    HeaderComponent,
    SidenavComponent,
    SmTableComponent,
    LoginView,
    DockerServiceLogsView,
    DockerServiceRemoveDialog
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    MaterialModule
  ],
  providers: [
    HeaderService,
    DockerServicesService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
    ],
  entryComponents: [
    DockerServiceRemoveDialog
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
