import { CustomerUpdateModule } from './user-management/update/customer-update.module';
import { SubscriptionManagementModule } from './subscription-management/subscription-management.module';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from '../config/authority.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access.service';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'user-management',
        canActivate: [UserRouteAccessService],
        data: {
          pageTitle: 'userManagement.home.title',
          authorities: [Authority.ADMIN, Authority.HR],
        },
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
      },
      {
        path: 'subscription-management',
        canActivate: [UserRouteAccessService],
        data: {
          pageTitle: 'userManagement.home.title',
          authorities: [Authority.ADMIN, Authority.HR],
        },
        loadChildren: () => import('./subscription-management/subscription-management.module').then(m => m.SubscriptionManagementModule),
      },
      {
        path: 'customer-update',
        canActivate: [UserRouteAccessService],
        data: {
          pageTitle: 'userManagement.home.title',
          authorities: [Authority.ADMIN, Authority.HR],
        },
        loadChildren: () => import('./user-management/update/customer-update.module').then(m => m.CustomerUpdateModule),
      },
      {
        path: 'docs',
        loadChildren: () => import('./docs/docs.module').then(m => m.DocsModule),
      },
      {
        path: 'configuration',
        loadChildren: () => import('./configuration/configuration.module').then(m => m.ConfigurationModule),
      },
      {
        path: 'health',
        loadChildren: () => import('./health/health.module').then(m => m.HealthModule),
      },
      {
        path: 'logs',
        loadChildren: () => import('./logs/logs.module').then(m => m.LogsModule),
      },
      {
        path: 'metrics',
        loadChildren: () => import('./metrics/metrics.module').then(m => m.MetricsModule),
      },
    ]),
  ],
})
export class AdminRoutingModule {}
