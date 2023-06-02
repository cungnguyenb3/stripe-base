import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CustomerUpdateComponent } from './customer-update.component';
import { customerUpdateRoute } from './customer-update.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(customerUpdateRoute)],
  declarations: [CustomerUpdateComponent],
})
export class CustomerUpdateModule {}
