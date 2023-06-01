import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { SubscriptionManagementComponent } from './list/subscription-management.component';
import { subscriptionManagementRoute } from './subscription-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(subscriptionManagementRoute)],
  declarations: [SubscriptionManagementComponent],
})
export class SubscriptionManagementModule {}
