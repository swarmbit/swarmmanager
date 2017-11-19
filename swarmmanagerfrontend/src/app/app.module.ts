import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MaterialModule } from './modules/material/material.module';
import { LoginView } from './views/login/login.view';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService } from './services/auth/auth.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
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

import {
  HammerGestureConfig,
  HAMMER_GESTURE_CONFIG,
} from '@angular/platform-browser';
import { DockerNetworksService } from './services/docker/networks/docker.networks.service';
import { SnackbarService } from './services/snackbar/snackbar.service';

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
    LoginView,
    NetworksView,
    NodesView
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MaterialModule,
    HttpClientModule,
    RoutingModule
  ],
  providers: [
    AuthService,
    ProgressBarService,
    ScreenService,
    HeaderService,
    UserService,
    SnackbarService,
    DockerSwarmService,
    DockerNetworksService,
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
  bootstrap: [AppComponent]
})
export class AppModule { }
