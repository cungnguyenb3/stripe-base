import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IUser } from '../user-management.model';

@Component({
  selector: 'jhi-user-management-detail-dialog',
  templateUrl: './user-management-detail-dialog.component.html',
  styleUrls: ['./user-management-detail-dialog.component.scss'],
})
export class UserManagementDetailDialogComponent {
  userInput: IUser | undefined;

  constructor(public activeModal: NgbActiveModal) {}

  onClose(): void {
    this.activeModal.close();
  }
}
