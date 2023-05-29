import { CanActivate, Route, Router } from '@angular/router';

import { Injectable } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { HomeComponent } from './home.component';
import { Authority } from '../config/authority.constants';
import { map, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LoginResolve implements CanActivate {
  constructor(private accountService: AccountService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        if (account) {
          if (account.authorities.includes(Authority.ADMIN) || account.authorities.includes(Authority.HR)) {
            this.router.navigate(['admin/user-management']);
          } else if (account.authorities.includes(Authority.PM) || account.authorities.includes(Authority.TECH_LEAD)) {
            this.router.navigate(['admin/candidates']);
          }
        } else {
          this.router.navigate(['login']);
        }

        return true;
      })
    );
  }
}
export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
  canActivate: [LoginResolve],
};
