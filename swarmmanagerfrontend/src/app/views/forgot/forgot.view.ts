import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiUtils } from '../../services/utils/api.utils';
import { UsersService } from '../../services/users/users.service';
import { SnackbarService } from '../../services/snackbar/snackbar.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot',
  styleUrls: ['forgot.view.scss'],
  templateUrl: 'forgot.view.html'
})
export class ForgotView implements OnInit  {

  resetPasswordForm: FormGroup;
  resetPasswordError: string;

  constructor(private usersService: UsersService, private snackbar: SnackbarService, private router: Router) {
  }

  ngOnInit(): void {
    this.initResetPasswordForm();
  }

  private initResetPasswordForm(): void {
    this.resetPasswordForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(4)]),
      'resetKey': new FormControl('', [Validators.required]),
      'password': new FormControl('', [Validators.required, Validators.minLength(6)]),
      'confirmPassword': new FormControl('', [Validators.required, Validators.minLength(6)]),
    });
  }

  resetPassword(): void {
    if (this.resetPasswordForm.valid) {
      const values = this.resetPasswordForm.value;
      const username = values['username'];
      const resetKey = values['resetKey'];
      const password = values['password'];
      const confirmPassword = values['confirmPassword'];
      if (password === confirmPassword) {
        this.resetPasswordError = null;
        this.usersService.resetPassword(username, resetKey, password).subscribe(
          () => {
            this.snackbar.showSuccess('Password successfully reset');
            this.router.navigate(['login']);
          },
          error => {
            const apiError = ApiUtils.getApiError(error);
            if (apiError === 'not-exist') {
              this.resetPasswordError = 'User does not exist.';
            } else if (apiError === 'not-match') {
              this.resetPasswordError = 'Invalid reset key.';
            }
          }
        );
      } else {
        this.resetPasswordError = 'Confirmation password does not match password.';
      }
    }
  }

}
