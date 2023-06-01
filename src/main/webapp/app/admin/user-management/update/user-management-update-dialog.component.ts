import { SubscriptionService } from './../../../entities/subscription/subscription.service';
import { PaymentMethodService } from './../../../entities/payment-method/payment-method.service';
import { PaymentMethod, IPaymentMethod } from './../../../entities/payment-method/payment-method.model';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IUser, User } from '../user-management.model';
import { EMAIL_REGEX } from '../../../shared/constants/input.constant';
import { UserManagementService } from '../service/user-management.service';
import { ISubscription, Subscription } from 'app/entities/subscription/subscription.model';
import { isPresent } from 'app/core/util/operators';

@Component({
  selector: 'jhi-user-management-update-dialog',
  templateUrl: './user-management-update-dialog.component.html',
})
export class UserManagementUpdateDialogComponent implements OnInit {
  userInput: IUser = {} as IUser;
  isSaving = false;
  isEdit = false;
  subscription: ISubscription = {} as ISubscription;
  paymentMethods: IPaymentMethod[] | null = null;
  currentPaymentMethod?: string;

  editForm = new FormGroup({
    paymentMethodControl: new FormControl(this.currentPaymentMethod, { validators: [Validators.required] }),
  });

  protected readonly EMAIL_REGEX = EMAIL_REGEX;

  constructor(
    public activeModal: NgbActiveModal,
    private userService: UserManagementService,
    private paymentMethodService: PaymentMethodService,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit(): void {
    this.isEdit = this.userInput.id != null;
    this.getAllPaymentMethod();
    console.error(this.subscription);
  }

  getAllPaymentMethod(): void {
    this.paymentMethodService.query().subscribe(res => {
      this.paymentMethods = res.body;
    });
  }

  compareById(fist: any, second: any): any {
    return fist && second && fist.id === second.id;
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

  edit(): void {
    // const userUpdated: User = {
    //   ...this.userInput,
    //   login: this.userInput.email,
    // };
    if (isPresent(this.currentPaymentMethod)) {
      this.subscription.paymentMethodId = this.currentPaymentMethod;
    }
    this.subscriptionService.update(this.subscription.id, this.subscription).subscribe({
      complete: () => {
        this.onClose(true);
      },
    });
  }

  add(): void {
    const userUpdated: User = {
      ...this.userInput,
      login: this.userInput.email,
    };
    this.userService.create(userUpdated).subscribe(() => {
      this.onClose(true);
    });
  }

  onPaymentMethodChange(): void {
    console.error(this.currentPaymentMethod);
  }

  onClose(added: boolean): void {
    this.activeModal.close(added);
  }

  disableSave(): boolean {
    if (this.editForm.dirty && this.editForm.valid) {
      return false;
    }
    return true;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.activeModal.close('success');
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
