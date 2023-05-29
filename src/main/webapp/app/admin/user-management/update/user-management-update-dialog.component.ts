import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IUser, User } from '../user-management.model';
import { EMAIL_REGEX } from '../../../shared/constants/input.constant';
import { UserManagementService } from '../service/user-management.service';

@Component({
  selector: 'jhi-user-management-update-dialog',
  templateUrl: './user-management-update-dialog.component.html',
})
export class UserManagementUpdateDialogComponent implements OnInit {
  userInput: IUser = {} as IUser;
  protected readonly EMAIL_REGEX = EMAIL_REGEX;
  authorities: Array<string> = [];
  oldEmail: string | undefined;
  isEdit: boolean = false;

  constructor(public activeModal: NgbActiveModal, private userService: UserManagementService) {}

  ngOnInit(): void {
    this.oldEmail = this.userInput.email;
    this.isEdit = this.userInput.id != null;

    this.userService.authorities().subscribe({
      next: value => {
        this.authorities = value;
      },
    });
  }

  editForm = new FormGroup({
    firstName: new FormControl(this.userInput.firstName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    lastName: new FormControl(this.userInput.lastName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    email: new FormControl(this.userInput.email, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern(EMAIL_REGEX)],
    }),
    authorities: new FormControl(this.userInput.authorities, {
      validators: [Validators.required],
    }),
  });

  email() {
    return this.editForm.get('email');
  }

  firstName() {
    return this.editForm.get('firstName');
  }

  lastName() {
    return this.editForm.get('lastName');
  }

  authority() {
    return this.editForm.get('authorities');
  }

  login() {
    return this.editForm.get('login');
  }

  validate(field: any): boolean {
    return field.dirty && field.invalid;
  }

  save() {
    if (!this.editForm.valid) {
      return;
    }

    if (this.userInput.id != null) {
      this.edit();
    } else {
      this.add();
    }
  }

  edit() {
    const userUpdated: User = {
      ...this.userInput,
      login: this.userInput.email,
    };
    this.userService.update(userUpdated).subscribe({
      complete: () => {
        this.onClose(true);
      },
    });
  }

  add() {
    const userUpdated: User = {
      ...this.userInput,
      login: this.userInput.email,
    };
    this.userService.create(userUpdated).subscribe({
      complete: () => {
        this.onClose(true);
      },
    });
  }

  onClose(added: boolean = false) {
    this.activeModal.close(added);
  }

  disableSave() {
    if (this.editForm.dirty && this.editForm.valid) {
      return false;
    }
    return true;
  }
}
