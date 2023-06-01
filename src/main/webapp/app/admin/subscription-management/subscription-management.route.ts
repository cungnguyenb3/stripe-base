import { SubscriptionManagementComponent } from './list/subscription-management.component';
import { Routes } from '@angular/router';

export const subscriptionManagementRoute: Routes = [
  {
    path: '',
    component: SubscriptionManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
];
