import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MaterialModule } from './modules/material/material.module';
import { LoginView } from './views/login/login.view';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService } from './services/auth/auth.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProgressBarService } from './services/progress.bar/progress.bar.service';
import { ProgressBarComponent } from './components/progress.bar/progress.bar.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ProgressBarInterceptor } from './interceptors/progress.bar.interceptor';
import { ShellComponent } from './components/shell/shell.component';
import { ScreenService } from './services/screen/screen.service';
import { UserComponent } from './components/user/user.component';
import { HeaderService } from './services/header/header.service';
import { UserService } from './services/user/user.service';
import { DockerSwarmService } from './services/docker/swarms/docker.swarms.service';
import { RoutingModule } from './modules/routing/routing.module';
import { NetworksView } from './views/networks/networks.view';
import { NodesView } from './views/nodes/nodes.view';
import 'hammerjs';
import { HammerGestureConfig, HAMMER_GESTURE_CONFIG } from '@angular/platform-browser';
import { DockerNetworksService } from './services/docker/networks/docker.networks.service';
import { SnackbarService } from './services/snackbar/snackbar.service';
import { ListControlBarComponent } from './components/list.control.bar/list.control.bar.component';
import { ListContainerComponent } from './components/list.container/list.container.component';
import { DockerNodesService } from './services/docker/nodes/docker.nodes.service';
import { ManageNetworkView } from './views/networks/manage/manage.network.view';
import { DockerNetworkResolver } from './resolvers/docker/networks/docker.network.resolver';
import { ConfirmationDialogComponent } from './components/confirmation.dialog/confirmation.dialog.component';
import { ServicesView } from './views/services/services.view';
import { ManageServicesView } from './views/services/manage/manage.services.view';
import { DockerServicesService } from './services/docker/services/docker.services.service';
import { CleanServiceImagePipe } from './pipes/clean.service.image.pipe';
import { RemoveServiceImageRepositoryPipe } from './pipes/remove.service.image.repository.pipe';
import { DockerServiceResolver } from './resolvers/docker/services/docker.service.resolver';
import { FormsService } from './services/utils/forms.service';
import { ManageServiceConfirmation } from './views/services/manage/manage.service.confirmation';
import { LogsView } from './views/services/logs/logs.view';
import { DockerServiceLogsResolver } from './resolvers/docker/services/docker.service.logs.resolver';
import { ProfileView } from './views/profile/profile.view';
import { BrowserService } from './services/utils/browser.service';
import { UserManagementView } from './views/user.management/user.management.view';
import { UsersService } from './services/users/users.service';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { VisitorGuard } from './guards/visitor.guard';
import { CreateUserView } from './views/create.user/create.user.view';
import { LoginGuard } from './guards/login.guard';
import { ResetKeyDialogComponent } from './views/create.user/reset.key.dialog/reset.key.dialog.component';
import { NotFoundView } from './views/not.found/not.found.view';
import { ForgotView } from './views/forgot/forgot.view';
import { ConfigsSecretsView } from './views/configs.secrets/configs.secrets.view';
import { DockerConfigsService } from './services/docker/configs/docker.configs.service';
import { DockerSecretsService } from './services/docker/secrets/docker.secrets.service';
import { ManageConfigSecretView } from './views/configs.secrets/manage/manage.config.secret.view';
import { DockerSecretsResolver } from './resolvers/docker/secrets/docker.secrets.resolver';
import { DockerConfigsResolver } from './resolvers/docker/configs/docker.configs.resolver';
import { ImportButtonComponent } from './components/import.button/import.button.component';
import { ClipboardModule } from 'ngx-clipboard';

declare const Hammer: any;

export class HammerConfig extends HammerGestureConfig  {
  buildHammer(element: HTMLElement) {
    return new Hammer(element, {
      touchAction: 'pan-y'
    });
  }
}

@NgModule({
  declarations: [
    AppComponent,
    ProgressBarComponent,
    ShellComponent,
    UserComponent,
    ListControlBarComponent,
    ListContainerComponent,
    ConfirmationDialogComponent,
    CleanServiceImagePipe,
    RemoveServiceImageRepositoryPipe,
    LoginView,
    NetworksView,
    ManageNetworkView,
    ServicesView,
    ManageServicesView,
    ManageServiceConfirmation,
    LogsView,
    NodesView,
    ProfileView,
    UserManagementView,
    CreateUserView,
    ResetKeyDialogComponent,
    NotFoundView,
    ForgotView,
    ConfigsSecretsView,
    ManageConfigSecretView,
    ImportButtonComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    HttpClientModule,
    RoutingModule,
    ClipboardModule
  ],
  providers: [
    AuthService,
    AuthGuard,
    AdminGuard,
    VisitorGuard,
    LoginGuard,
    ProgressBarService,
    ScreenService,
    HeaderService,
    UserService,
    SnackbarService,
    DockerSwarmService,
    DockerServicesService,
    DockerServiceResolver,
    DockerServiceLogsResolver,
    DockerConfigsService,
    DockerSecretsService,
    FormsService,
    DockerNetworksService,
    DockerNodesService,
    DockerNetworkResolver,
    DockerSecretsResolver,
    DockerConfigsResolver,
    BrowserService,
    UsersService,
    {
      provide: HAMMER_GESTURE_CONFIG,
      useClass: HammerConfig ,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ProgressBarInterceptor,
      multi: true
    }
  ],
  entryComponents: [
    ConfirmationDialogComponent,
    ResetKeyDialogComponent,
    ManageServiceConfirmation
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
