import { NgModule } from '@angular/core';
import { CustomerUpdateComponent } from './customer-update.component';
import { SharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { customerUpdateRoute } from './customer-update.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(customerUpdateRoute)],
  declarations: [CustomerUpdateComponent],
})
export class CustomerUpdateModule {}
