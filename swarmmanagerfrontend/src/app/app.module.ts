import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from './material/material.module';

import { AppComponent } from './app.component';
import { ShellComponent } from './shell/shell.component';
import { ServicesComponent } from './services/services.component';
import { UserComponent } from 'app/user/user.component';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SwarmComponent } from './swarm/swarm.component';
import { NodesComponent } from './nodes/nodes.component';
import { NetworksComponent } from './networks/networks.component';
import { SecretsComponent } from './secrets/secrets.component';
import { RegistriesComponent } from './registries/registries.component';
import { AuditComponent } from './audit/audit.component';
import { UserManagementComponent } from './user-management/user.management.component';
import { RoutingService } from './routing/routing.service';
import { ServicesService } from './services/services-service/services.service';
import { CleanServiceImagePipe } from './services/pipes/clean.service.image.pipe';
import { ServicesDetailsComponent } from './services/service-details/services.details.component';
import { HeaderComponent } from './shell/header/header.component';
import { SidenavComponent } from './shell/navbar/navbar.component';
import 'hammerjs';

const appRoutes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'services/create', component: ServicesDetailsComponent },
  { path: 'services/details/:id', component: ServicesDetailsComponent },
  { path: 'swarm', component: SwarmComponent },
  { path: 'nodes', component: NodesComponent },
  { path: 'networks', component: NetworksComponent },
  { path: 'secrets', component: SecretsComponent },
  { path: 'registries', component: RegistriesComponent },
  { path: 'audit', component: AuditComponent },
  { path: 'users', component: UserManagementComponent }
];
@NgModule({
  declarations: [
    AppComponent,
    ShellComponent,
    ServicesComponent,
    ServicesDetailsComponent,
    UserComponent,
    DashboardComponent,
    SwarmComponent,
    NodesComponent,
    NetworksComponent,
    SecretsComponent,
    RegistriesComponent,
    AuditComponent,
    UserManagementComponent,
    CleanServiceImagePipe,
    HeaderComponent,
    SidenavComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes),
    MaterialModule
  ],
  providers: [RoutingService, ServicesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
