import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';
import { Router } from '@angular/router';
import { Authority } from 'app/config/authority.constants';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider, private router: Router) {}

  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider.login(credentials).pipe(mergeMap(() => this.accountService.identity(true)));
  }

  logout(): void {
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }

  redirectToLandingPage(account?: Account): void {
    if (account) {
      if (this.accountService.hasAnyAuthority([Authority.ADMIN]) || this.accountService.hasAnyAuthority([Authority.HR])) {
        this.router.navigate(['/admin/user-management']);
      } else if (this.accountService.hasAnyAuthority([Authority.TECH_LEAD]) || this.accountService.hasAnyAuthority([Authority.PM])) {
        this.router.navigate(['/candidate-management']);
      }
    } else {
      this.router.navigate(['/']);
    }
  }
}
