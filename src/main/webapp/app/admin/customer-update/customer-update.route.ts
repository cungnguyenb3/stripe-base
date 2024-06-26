import { Routes } from '@angular/router';
import { CustomerUpdateComponent } from './customer-update.component';

export const customerUpdateRoute: Routes = [
  {
    path: ':customerId',
    component: CustomerUpdateComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
];
