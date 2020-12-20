import { Component, OnInit } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { BrowserService } from '../../services/utils/browser.service';
import { User } from '../../services/user/user';
import { UsersService } from '../../services/users/users.service';
import { ViewUtils } from '../view.utils';
import { UsersRoles } from '../../services/users/users.roles';
import { ConfirmationDialogComponent } from '../../components/confirmation.dialog/confirmation.dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { SnackbarService } from '../../services/snackbar/snackbar.service';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-user-management',
  styleUrls: ['user.management.view.scss'],
  templateUrl: 'user.management.view.html'
})
export class UserManagementView extends BaseView implements OnInit {

  filter = '';
  users: User[] = [];
  usersRoles = new Map<string, any>();

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private browserService: BrowserService,
              private usersService: UsersService,
              public dialog: MatDialog,
              public snackbarService: SnackbarService
  ) {
    super(headerService, route, swarmService, userService, browserService);
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.usersService.getUsers().subscribe(users =>{
      this.users = users;
      for(const user of users) {
        this.usersRoles.set(user.username, {
          username: user.username,
          role: user.role
        });
      }
    });
  }

  getUsers(): User[] {
    return ViewUtils.getFilteredArray(this.filter, this.users, 'username');
  }

  getRoles(): string[] {
    return UsersRoles.getRolesList();
  }

  saveRole(username: string): void {
    const role = this.usersRoles.get(username).role;
    this.usersService.changeRole(username, role).subscribe(
      () => {
        this.snackbarService.showSuccess('Changed ' + username + ' role to ' + new TitleCasePipe().transform(role));
      },
      (error) => {
        this.snackbarService.showError('Failed to Change ' + username + ' role to ' +  new TitleCasePipe().transform(role));
      });
  }

  openDialog(username: string): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '15rem',
      data: {
        title: 'Remove User'
      }
    });

    this.subscriptions.push(dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.usersService.removeUser(username).subscribe(
          () => {
            this.loadUsers();
            this.snackbarService.showSuccess('Removed user ' + username);
          },
          (error) => {
            this.snackbarService.showError('Failed to removed user ' + username);
          });
      }
    }));
  }
}
