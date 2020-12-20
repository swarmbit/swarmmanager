import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../../services/users/users.service';
import { CreateUserResponse } from '../../services/users/create.user.response';
import { SnackbarService } from '../../services/snackbar/snackbar.service';
import { MatDialog } from '@angular/material/dialog';
import { ResetKeyDialogComponent } from './reset.key.dialog/reset.key.dialog.component';
import { ApiUtils } from '../../services/utils/api.utils';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-create-user',
  styleUrls: ['create.user.view.scss'],
  templateUrl: 'create.user.view.html'
})
export class CreateUserView implements OnInit, OnDestroy  {

  createUserForm: FormGroup;
  createUserError: string;
  subscriptions: Subscription[] = [];

  constructor(private usersService: UsersService,
              public dialog: MatDialog,
              public snackbarService: SnackbarService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.createUserFormInit();
  }

  private createUserFormInit(): void {
    this.createUserForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(4)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(6)]),
      'confirmPassword': new FormControl('', [Validators.required, Validators.minLength(6)]),
    });
  }

  createUser(): void {
    if (this.createUserForm.valid) {
      const values = this.createUserForm.value;
      const username = values['username'];
      const password = values['password'];
      const confirmPassword = values['confirmPassword'];
      if (password === confirmPassword) {
        this.createUserError = null;
        this.usersService.createUser(username, password).subscribe(
          (createUserResponse: CreateUserResponse) => {
            this.snackbarService.showSuccess('User ' + username + ' successfuly created');
            this.openDialog(atob(createUserResponse.resetPasswordKey));
          },
          error => {
            const apiError = ApiUtils.getApiError(error);
            if (apiError === 'exist') {
              this.createUserError = 'Username already exists.';
            }
          }
        );
      } else {
        this.createUserError = 'Confirmation password does not match password.';
      }
    }
  }

  openDialog(resetKey: string): void {
    const dialogRef = this.dialog.open(ResetKeyDialogComponent, {
      width: '30rem',
      data: {
        title: 'Reset Key',
        resetKey: resetKey
      }
    });

    this.subscriptions.push(dialogRef.afterClosed().subscribe(() => {
      this.router.navigate(['/login']);
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
