import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './list/user-management.component';
import { UserManagementDeleteDialogComponent } from './delete/user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';
import { UserManagementUpdateDialogComponent } from './update/user-management-update-dialog.component';
import { UserManagementDetailDialogComponent } from './detail/user-management-detail-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [
    UserManagementComponent,
    UserManagementDeleteDialogComponent,
    UserManagementUpdateDialogComponent,
    UserManagementDetailDialogComponent,
  ],
})
export class UserManagementModule {}
