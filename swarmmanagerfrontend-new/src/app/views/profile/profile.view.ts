import { Component, OnInit } from '@angular/core';
import { BaseView } from '../base.view';
import { HeaderService } from '../../services/header/header.service';
import { DockerSwarmService } from '../../services/docker/swarms/docker.swarms.service';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { BrowserService } from '../../services/utils/browser.service';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.view.html',
  styleUrls: ['./profile.view.css']
})
export class ProfileView  extends BaseView implements OnInit {

  userDataForm: FormGroup;
  passwordForm: FormGroup;
  displayName = 'displayName';
  username = 'username';
  passwordChangeError: string;

  constructor(headerService: HeaderService,
              public swarmService: DockerSwarmService,
              private userService: UserService,
              private route: ActivatedRoute,
              private browserService: BrowserService
  ) {
    super(headerService, route, swarmService, userService, browserService);
    if (this.browserService.getPreviousUrl() === '/login') {
      this.browserService.reset();
    } else {
      this.enableBackArrow();
    }
  }

  ngOnInit(): void {
    this.initUserDataForm();
    this.initPasswordForm();
  }

  private initUserDataForm(): void {
    this.userDataForm = new FormGroup({
      'username': new FormControl({ value: '', disabled: true }),
      'displayName': new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)]),
    });
    this.userService.getUser().then((user) => {
      this.userDataForm.get(this.displayName).setValue(user.displayName);
      this.userDataForm.get(this.username).setValue(user.username);
    });
  }

  private initPasswordForm(): void {
    this.passwordForm = new FormGroup({
      'currentPassword': new FormControl('', [Validators.required]),
      'newPassword': new FormControl('', [Validators.required, Validators.minLength(6)]),
      'confirmNewPassword': new FormControl('', [Validators.required, Validators.minLength(6)]),
    });
  }

  saveDisplayName(): void {
    if (this.userDataForm.valid) {
      this.userService.setDisplayName(this.userDataForm.get(this.displayName).value).subscribe();
    }
  }

  changePassword(): void {
    if (this.passwordForm.valid) {
      const values = this.passwordForm.value;
      const currentPassword = values['currentPassword'];
      const newPassword = values['newPassword'];
      const confirmNewPassword = values['confirmNewPassword'];
      if (newPassword === confirmNewPassword) {
        this.passwordChangeError = null;
        this.userService.changePassword(currentPassword, newPassword).subscribe(
          () => {
            this.resetForm(this.passwordForm);
          },
          error => {
            if (error === 'not-match') {
              this.passwordChangeError = 'Invalid current password.';
            }
          }
        );
      } else {
        this.passwordChangeError = 'Confirmation password does not match new password.';
      }

    }
  }

  resetForm(formGroup: FormGroup) {
    let control: AbstractControl = null;
    formGroup.reset();
    formGroup.markAsUntouched();
    Object.keys(formGroup.controls).forEach((name) => {
      control = formGroup.controls[name];
      control.setErrors(null);
    });
  }
}
